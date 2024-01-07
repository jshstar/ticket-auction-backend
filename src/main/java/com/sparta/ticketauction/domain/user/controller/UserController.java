package com.sparta.ticketauction.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.ticketauction.domain.user.request.UserCreateRequest;
import com.sparta.ticketauction.domain.user.service.UserService;
import com.sparta.ticketauction.global.response.ApiResponse;
import com.sparta.ticketauction.global.response.SuccessCode;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<ApiResponse> signup(@RequestBody @Valid UserCreateRequest request) {
		userService.signup(request);
		return ResponseEntity.status(SuccessCode.SUCCESS_USER_SIGN_UP.getHttpStatus())
			.body(
				ApiResponse.of(SuccessCode.SUCCESS_USER_SIGN_UP.getCode(),
					SuccessCode.SUCCESS_USER_SIGN_UP.getMessage())
			);
	}
}
