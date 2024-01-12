package com.sparta.ticketauction.domain.user.controller;

import static com.sparta.ticketauction.global.response.SuccessCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.request.UserCreateRequest;
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
		userService.signup(request);
		return ResponseEntity.status(SUCCESS_USER_SIGN_UP.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_USER_SIGN_UP.getCode(),
					SUCCESS_USER_SIGN_UP.getMessage()
				)
			);
	}

	@PutMapping("/{user_id}")
	public ResponseEntity<ApiResponse> updateUserNicknameInfo(
		@CurrentUser User user,
		@PathVariable Long user_id,
		@RequestBody @Valid UserUpdateRequest request
	) {
		userService.updateUserInfo(user, user_id, request);
		return ResponseEntity.status(SUCCESS_UPDATE_USER_INFO.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_UPDATE_USER_INFO.getCode(),
					SUCCESS_UPDATE_USER_INFO.getMessage()
				)
			);
	}

	@GetMapping("/{user_id}")
	public ResponseEntity<ApiResponse> getUserInfo(
		@CurrentUser User user,
		@PathVariable Long user_id
	) {
		UserResponse response = userService.gerUserInfo(user, user_id);
		return ResponseEntity.status(SUCCESS_GET_USER_INFO.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_GET_USER_INFO.getCode(),
					SUCCESS_UPDATE_USER_INFO.getMessage(),
					response
				)
			);
	}

}
