package com.sparta.ticketauction.domain.admin.controller;

import static com.sparta.ticketauction.global.response.SuccessCode.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.ticketauction.domain.admin.request.PlaceRequest;
import com.sparta.ticketauction.domain.admin.response.PlaceResponse;
import com.sparta.ticketauction.domain.admin.service.AdminServiceImpl;
import com.sparta.ticketauction.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class AdminController {

	private final AdminServiceImpl adminService;

	// 공연장 추가
	@PostMapping("/admin/place")
	public ResponseEntity<ApiResponse<List<PlaceResponse>>> createPlace(
		@Valid @RequestBody PlaceRequest placeRequest) {
		List<PlaceResponse> placeResponse = adminService.createPlace(placeRequest);
		return ResponseEntity
			.status(SUCCESS_PLACE_CREATE.getHttpStatus())
			.body(
				new ApiResponse<>(
					SUCCESS_PLACE_CREATE.getCode(),
					SUCCESS_PLACE_CREATE.getMessage(),
					placeResponse
				)
			);
	}

}
