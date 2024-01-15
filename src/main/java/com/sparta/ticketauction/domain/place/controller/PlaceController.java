package com.sparta.ticketauction.domain.place.controller;

import static com.sparta.ticketauction.global.response.SuccessCode.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.ticketauction.domain.place.response.PlaceGetResponse;
import com.sparta.ticketauction.domain.place.service.PlaceService;
import com.sparta.ticketauction.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vi")
public class PlaceController {
	private final PlaceService placeService;

	// 공연장 전체 조회
	@GetMapping("/places")
	public ResponseEntity<ApiResponse<List<PlaceGetResponse>>> getAllPlace() {
		List<PlaceGetResponse> placeGetResponses = placeService.getAllPlace();
		return ResponseEntity
			.status(
				SUCCESS_GET_ALL_PLACE.getHttpStatus())
			.body(
				ApiResponse.of(SUCCESS_GET_ALL_PLACE.getCode(),
					SUCCESS_GET_ALL_PLACE.getMessage(),
					placeGetResponses)
			);
	}

}
