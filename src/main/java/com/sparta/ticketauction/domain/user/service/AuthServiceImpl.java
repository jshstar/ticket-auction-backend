package com.sparta.ticketauction.domain.user.service;

import static com.sparta.ticketauction.global.exception.ErrorCode.*;
import static com.sparta.ticketauction.global.jwt.JwtUtil.*;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.ticketauction.domain.user.entity.constant.Role;
import com.sparta.ticketauction.domain.user.request.SmsMessageRequest;
import com.sparta.ticketauction.domain.user.request.UserForVerificationRequest;
import com.sparta.ticketauction.domain.user.response.SmsResponse;
import com.sparta.ticketauction.global.exception.ApiException;
import com.sparta.ticketauction.global.jwt.JwtUtil;
import com.sparta.ticketauction.global.util.LettuceUtils;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private static final String PREFIX_REFRESH_TOKEN = "RefreshToken: ";
	private static final String PREFIX_LOGOUT = "Logout: ";
	private static final long VERIFY_TIME = 5 * 60 * 1000L;
	private final ObjectMapper objectMapper = new ObjectMapper();

	private final JwtUtil jwtUtil;
	private final LettuceUtils lettuceUtils;
	private final UserService userService;

	@Value("${naver-cloud.access-key}")
	private String accessKey;
	@Value("${naver-cloud.secret-key}")
	private String secretKey;
	@Value("${naver-cloud.service-id}")
	private String serviceId;
	@Value("${naver-cloud.sender-phone}")
	private String senderPhone;

	@Override
	@Transactional
	public void logout(HttpServletRequest request) {
		String accessToken = jwtUtil.getAccessTokenFromRequestHeader(request);

		jwtUtil.validateToken(accessToken);

		Claims claims = jwtUtil.getUserInfoFromToken(accessToken);
		String username = claims.getSubject();
		Date expiration = claims.getExpiration();
		Integer remainingTime = jwtUtil.getRemainingTime(expiration);

		lettuceUtils.delete(PREFIX_REFRESH_TOKEN + username);
		lettuceUtils.save(PREFIX_LOGOUT + username, accessToken, remainingTime);
	}

	@Override
	@Transactional
	public SmsResponse verifyPhone(UserForVerificationRequest userForVerificationRequest) {
		if (userService.isExistedPhoneNumber(userForVerificationRequest.getTo())) {
			throw new ApiException(EXISTED_USER_PHONE_NUMBER);
		}

		Random random = new Random();
		int verificationNumber = random.nextInt(888888) + 111111;

		String method = "POST";
		String hostUrl = "https://sens.apigw.ntruss.com";
		String requestUrl = "/sms/v2/services/" + serviceId + "/messages";
		String time = Long.toString(System.currentTimeMillis());

		SmsResponse smsResponse;

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("x-ncp-apigw-timestamp", time);
			headers.set("x-ncp-iam-access-key", accessKey);
			headers.set("x-ncp-apigw-signature-v2", getSignature(time));

			List<UserForVerificationRequest> messages = new ArrayList<>();
			messages.add(userForVerificationRequest);

			SmsMessageRequest request = SmsMessageRequest.builder()
				.type("SMS")
				.contentType("COMM")
				.countryCode("82")
				.from(senderPhone)
				.messages(messages)
				.content("[Ticket Auction]\n인증 번호: " + verificationNumber + "\n5분 이내로 입력해주세요.")
				.build();

			String body = objectMapper.writeValueAsString(request);

			HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);

			RestTemplate restTemplate = new RestTemplate();
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

			smsResponse = restTemplate.postForObject(
				new URI(hostUrl + requestUrl),
				httpEntity,
				SmsResponse.class
			);
			smsResponse.setVerificationNumbers(String.valueOf(verificationNumber));

		} catch (
			UnsupportedEncodingException
			| NoSuchAlgorithmException
			| InvalidKeyException
			| JsonProcessingException
			| URISyntaxException e
		) {
			throw new ApiException(INTERNAL_SERVER_ERROR);
		}

		lettuceUtils.save(
			"[Verification]" + userForVerificationRequest.getTo(),
			String.valueOf(verificationNumber),
			VERIFY_TIME
		);

		return smsResponse;
	}

	@Override
	@Transactional
	public void reissue(HttpServletRequest request, HttpServletResponse response) {
		String refreshToken = jwtUtil.resolveRefreshToken(request);

		// 토큰 검증
		jwtUtil.validateToken(refreshToken);

		// 유저 정보 추출
		Claims claims = jwtUtil.getUserInfoFromToken(refreshToken);
		String username = claims.getSubject();
		Role role = Role.valueOf((String)claims.get("auth"));
		Long id = Long.parseLong(claims.get("identify").toString());

		if (!lettuceUtils.get(REFRESH_TOKEN_HEADER + " " + username).equals(refreshToken)) {
			throw new ApiException(INVALID_JWT_TOKEN);
		}

		String newAccessToken = jwtUtil.createAccessToken(id, username, role);
		String newRefreshToken = jwtUtil.createRefreshToken(id, username, role);

		jwtUtil.setAccessTokenInHeader(response, newAccessToken);
		jwtUtil.setRefreshTokenInCookie(response, newRefreshToken);

		lettuceUtils.delete(REFRESH_TOKEN_HEADER + " " + username);
		lettuceUtils.save(
			REFRESH_TOKEN_HEADER + " " + username,
			jwtUtil.substringToken(newRefreshToken),
			REFRESH_TOKEN_TIME
		);
	}

	private String getSignature(String time)
		throws UnsupportedEncodingException,
		NoSuchAlgorithmException,
		InvalidKeyException {
		String space = " ";
		String newLine = "\n";
		String method = "POST";
		String url = "/sms/v2/services/" + this.serviceId + "/messages";
		String accessKey = this.accessKey;
		String secretKey = this.secretKey;

		String message = new StringBuilder()
			.append(method)
			.append(space)
			.append(url)
			.append(newLine)
			.append(time)
			.append(newLine)
			.append(accessKey)
			.toString();

		SecretKeySpec signingKey = new SecretKeySpec(
			secretKey.getBytes("UTF-8"),
			"HmacSHA256"
		);
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(signingKey);

		byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
		String encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);

		return encodeBase64String;
	}

}
