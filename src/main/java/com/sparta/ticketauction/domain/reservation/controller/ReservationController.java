package com.sparta.ticketauction.domain.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.ticketauction.domain.reservation.request.ReservationCreateRequest;
import com.sparta.ticketauction.domain.reservation.response.ReservationDetailResponse;
import com.sparta.ticketauction.domain.reservation.service.ReservationService;
import com.sparta.ticketauction.global.exception.ApiException;
import com.sparta.ticketauction.global.exception.ErrorCode;
import com.sparta.ticketauction.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

	private final ReservationService reservationService;

	@PostMapping("/{sequenceId}/{seatId}")
	public ResponseEntity<ApiResponse<ReservationDetailResponse>> reserve(
		@PathVariable Long sequenceId,
		@PathVariable Long seatId,
		@RequestBody ReservationCreateRequest request,
		User user // TODO: 유저 주입해야 함
	) {
		try {
			ReservationDetailResponse response =
				reservationService.reserve(user, seatId, sequenceId, request);

			return ResponseEntity.ok(
				ApiResponse.of(SUCCESS_RESERVE.getCode(), SUCCESS_RESERVE.getMessage(), response)
			);
		} catch (ObjectOptimisticLockingFailureException e) {
			throw new ApiException(ErrorCode.ALREADY_RESERVED_SEAT);
		}
	}
}
