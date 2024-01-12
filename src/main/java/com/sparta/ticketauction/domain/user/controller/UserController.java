package com.sparta.ticketauction.domain.user.controller;

import static com.sparta.ticketauction.global.response.SuccessCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.request.UserCreateRequest;
import com.sparta.ticketauction.domain.user.request.UserNicknameUpdateRequest;
import com.sparta.ticketauction.domain.user.request.UserPhoneUpdateRequest;
import com.sparta.ticketauction.domain.user.service.UserService;
import com.sparta.ticketauction.global.annotaion.CurrentUser;
import com.sparta.ticketauction.global.response.ApiResponse;

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
		return ResponseEntity.status(SUCCESS_USER_SIGN_UP.getHttpStatus())
			.body(
				ApiResponse.of(SUCCESS_USER_SIGN_UP.getCode(),
					SUCCESS_USER_SIGN_UP.getMessage()
				)
			);
	}

	@PutMapping("/{user_id}/nickname")
	public ResponseEntity<ApiResponse> updateUserNicknameInfo(
		@CurrentUser User user,
		@PathVariable Long user_id,
		@RequestBody @Valid UserNicknameUpdateRequest request
	) {
		userService.updateUserNicknameInfo(user, user_id, request);
		return ResponseEntity.status(SUCCESS_UPDATE_USER_NICKNAME.getHttpStatus())
			.body(
				ApiResponse.of(SUCCESS_UPDATE_USER_NICKNAME.getCode(),
					SUCCESS_UPDATE_USER_NICKNAME.getMessage()
				)
			);
	}

	@PutMapping("/{user_id}/phone")
	public ResponseEntity<ApiResponse> updateUserPhoneInfo(
		@CurrentUser User user,
		@PathVariable Long user_id,
		@RequestBody @Valid UserPhoneUpdateRequest request
	) {
		userService.updateUserPhoneInfo(user, user_id, request);
		return ResponseEntity.status(SUCCESS_UPDATE_USER_PHONE.getHttpStatus())
			.body(
				ApiResponse.of(SUCCESS_UPDATE_USER_PHONE.getCode(),
					SUCCESS_UPDATE_USER_PHONE.getMessage()
				)
			);
	}
}
