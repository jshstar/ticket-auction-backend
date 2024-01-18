package com.sparta.ticketauction.domain.user.controller;

import static com.sparta.ticketauction.global.response.SuccessCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.request.UserCreateRequest;
import com.sparta.ticketauction.domain.user.request.UserDeleteRequest;
import com.sparta.ticketauction.domain.user.request.UserPasswordUpdateRequest;
import com.sparta.ticketauction.domain.user.request.UserUpdateRequest;
import com.sparta.ticketauction.domain.user.response.UserResponse;
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
		UserResponse response = userService.signup(request);
		return ResponseEntity.status(SUCCESS_USER_SIGN_UP.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_USER_SIGN_UP.getCode(),
					SUCCESS_USER_SIGN_UP.getMessage(),
					response
				)
			);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<ApiResponse> updateUserInfo(
		@CurrentUser User user,
		@PathVariable Long userId,
		@RequestBody @Valid UserUpdateRequest request
	) {
		UserResponse response = userService.updateUserInfo(user, userId, request);
		return ResponseEntity.status(SUCCESS_UPDATE_USER_INFO.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_UPDATE_USER_INFO.getCode(),
					SUCCESS_UPDATE_USER_INFO.getMessage(),
					response
				)
			);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<ApiResponse> getUserInfo(
		@CurrentUser User user,
		@PathVariable Long userId
	) {
		UserResponse response = userService.getUserInfo(user, userId);
		return ResponseEntity.status(SUCCESS_GET_USER_INFO.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_GET_USER_INFO.getCode(),
					SUCCESS_GET_USER_INFO.getMessage(),
					response
				)
			);
	}

	@PatchMapping("/{userId}")
	public ResponseEntity<ApiResponse> updateUserPassword(
		@CurrentUser User user,
		@PathVariable Long userId,
		@RequestBody @Valid UserPasswordUpdateRequest request
	) {
		userService.updateUserPassword(user, userId, request);
		return ResponseEntity.status(SUCCESS_UPDATE_USER_PASSWORD.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_UPDATE_USER_PASSWORD.getCode(),
					SUCCESS_UPDATE_USER_PASSWORD.getMessage()
				)
			);
	}

	@DeleteMapping
	public ResponseEntity<ApiResponse> deleteUser(
		@CurrentUser User user,
		@RequestBody UserDeleteRequest request
	) {
		userService.deleteUser(user, request);
		return ResponseEntity.status(SUCCESS_DELETE_USER.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_DELETE_USER.getCode(),
					SUCCESS_DELETE_USER.getMessage()
				)
			);
	}
}
