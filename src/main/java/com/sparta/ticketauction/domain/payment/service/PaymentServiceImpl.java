package com.sparta.ticketauction.domain.payment.service;

import static com.sparta.ticketauction.global.exception.ErrorCode.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.sparta.ticketauction.domain.payment.entity.Payment;
import com.sparta.ticketauction.domain.payment.repository.PaymentRepository;
import com.sparta.ticketauction.domain.payment.request.PaymentFromUserRequest;
import com.sparta.ticketauction.domain.payment.request.PaymentToApiRequest;
import com.sparta.ticketauction.domain.payment.response.PaymentSuccessResponse;
import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.service.PointService;
import com.sparta.ticketauction.domain.user.service.UserService;
import com.sparta.ticketauction.global.exception.ApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final UserService userService;
	private final PointService pointService;
	private final PaymentRepository paymentRepository;

	@Value("${TOSS_SECRET_KEY}")
	private String secretKey;
	@Value("${TOSS_CLIENT_KEY}")
	private String clientKey;
	@Value("${payment.toss.success-url}")
	private String successUrl;
	@Value("${payment.toss.fail-url}")
	private String failUrl;

	@Override
	@Transactional
	public PaymentToApiRequest requestPayment(User loginUser, PaymentFromUserRequest request) {
		User user = userService.findByUserId(loginUser.getId());

		Payment payment = request.toEntity(user);
		paymentRepository.save(payment);

		return PaymentToApiRequest.from(payment, successUrl, failUrl);
	}

	@Override
	@Transactional
	public PaymentSuccessResponse successPayment(String jsonBody) {
		JSONParser parser = new JSONParser();

		String orderId;
		String amount;
		String paymentKey;

		try {
			// 클라이언트에서 받은 JSON 요청 바디
			JSONObject requestData = (JSONObject)parser.parse(jsonBody);
			paymentKey = (String)requestData.get("paymentKey");
			orderId = (String)requestData.get("orderId");
			amount = (String)requestData.get("amount");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		Long amountLong = Long.parseLong(amount);
		Payment payment = verifyPayment(orderId, amountLong);
		payment.addPaymentKey(paymentKey);

		User user = userService.findByUserId(payment.getUser().getId());
		pointService.chargePoint(user, amountLong, orderId);

		return requestPaymentAccept(paymentKey, orderId, amountLong);

	}

	@Override
	public String getKey() {
		return this.clientKey;
	}

	private PaymentSuccessResponse requestPaymentAccept(String paymentKey, String orderId, Long amount) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = getHeaders();
		JSONObject params = new JSONObject();
		params.put("orderId", orderId);
		params.put("amount", amount);

		PaymentSuccessResponse response = null;

		try {
			response = restTemplate.postForObject("https://api.tosspayments.com/v1/payments/" + paymentKey,
				new HttpEntity<>(params, headers),
				PaymentSuccessResponse.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return response;
	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		String encodedAuthKey = new String(
			Base64.getEncoder().encode((secretKey + ":").getBytes(StandardCharsets.UTF_8))
		);

		headers.setBasicAuth(encodedAuthKey);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		return headers;
	}

	private Payment verifyPayment(String orderId, Long amount) {
		Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(
			() -> new ApiException(NOT_FOUND_ORDER_ID)
		);

		if (!payment.getAmount().equals(amount)) {
			throw new ApiException(NOT_EQUALS_AMOUNT);
		}

		return payment;
	}
}
