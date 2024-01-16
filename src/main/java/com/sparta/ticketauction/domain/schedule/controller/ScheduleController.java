package com.sparta.ticketauction.domain.schedule.controller;

import static com.sparta.ticketauction.global.response.SuccessCode.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.ticketauction.domain.schedule.response.ScheduleGetResponse;
import com.sparta.ticketauction.domain.schedule.service.ScheduleService;
import com.sparta.ticketauction.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ScheduleController {

	private final ScheduleService scheduleService;

	// 해당 공연에 대한 전 회차 조회
	@GetMapping("/goods/{goodsId}/schedules")
	public ResponseEntity<ApiResponse<List<ScheduleGetResponse>>> getAllSequence(Long goodsId) {
		List<ScheduleGetResponse> scheduleGetResponses = scheduleService.getAllSchedule(goodsId);

		return ResponseEntity
			.status(SUCCESS_GET_ALL_SCHEDULE.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_GET_ALL_SCHEDULE.getCode(),
					SUCCESS_GET_ALL_SCHEDULE.getMessage(),
					scheduleGetResponses)
			);
	}

}
