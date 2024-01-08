package com.sparta.ticketauction.domain.user.controller;

import static com.sparta.ticketauction.global.response.SuccessCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
}

