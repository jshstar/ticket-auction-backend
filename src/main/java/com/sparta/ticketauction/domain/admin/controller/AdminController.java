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
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

	private final AdminServiceImpl adminService;

	@PostMapping("/admin/places")
	public ResponseEntity<ApiResponse<List<PlaceResponse>>> createPlaceAndZone(
		@Valid @RequestBody PlaceRequest placeRequest
	) {
		List<PlaceResponse> placeResponseList = adminService.createPlaceAndZone(placeRequest);
		return ResponseEntity
			.status(SUCCESS_PLACE_AND_ZONE_CREATE.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_PLACE_AND_ZONE_CREATE.getCode(),
					SUCCESS_PLACE_AND_ZONE_CREATE.getMessage(),
					placeResponseList)
			);
	}

}
