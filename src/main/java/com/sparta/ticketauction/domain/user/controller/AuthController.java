package com.sparta.ticketauction.domain.user.controller;

import static com.sparta.ticketauction.global.response.SuccessCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.ticketauction.domain.user.request.UserForVerificationRequest;
import com.sparta.ticketauction.domain.user.response.SmsResponse;
import com.sparta.ticketauction.domain.user.service.AuthService;
import com.sparta.ticketauction.global.response.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/logout")
	public ResponseEntity<ApiResponse> logout(HttpServletRequest request) {
		authService.logout(request);
		return ResponseEntity.status(SUCCESS_USER_LOGOUT.getHttpStatus())
			.body(ApiResponse.of(SUCCESS_USER_LOGOUT.getCode(), SUCCESS_USER_LOGOUT.getMessage()));
	}

	@PostMapping("/signup/sms")
	public ResponseEntity<ApiResponse> verifyPhone(@RequestBody UserForVerificationRequest request) {
		SmsResponse response = authService.verifyPhone(request);
		return ResponseEntity.status(SUCCESS_SEND_VERIFICATION_NUMBER_BY_SMS.getHttpStatus())
			.body(ApiResponse.of(
					SUCCESS_SEND_VERIFICATION_NUMBER_BY_SMS.getCode(),
					SUCCESS_SEND_VERIFICATION_NUMBER_BY_SMS.getMessage(),
					response
				)
			);
	}

}

