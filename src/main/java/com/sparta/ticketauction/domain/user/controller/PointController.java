package com.sparta.ticketauction.domain.user.controller;

import static com.sparta.ticketauction.global.response.SuccessCode.*;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.service.PointService;
import com.sparta.ticketauction.global.annotaion.CurrentUser;
import com.sparta.ticketauction.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/points")
public class PointController {

	private PointService pointService;

	@GetMapping("/charge")
	public ResponseEntity<ApiResponse> getChargePointLogList(
		@CurrentUser User user,
		@PageableDefault(size = 15, sort = "createdAt", direction = Sort.Direction.DESC)
		Pageable pageable
	) {
		var response = pointService.getChargePointLogList(user, pageable);
		return ResponseEntity.status(SUCCESS_GET_CHARGE_POINT_LOG_LIST.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_GET_CHARGE_POINT_LOG_LIST.getCode(),
					SUCCESS_GET_CHARGE_POINT_LOG_LIST.getMessage(),
					response
				)
			);
	}
}
