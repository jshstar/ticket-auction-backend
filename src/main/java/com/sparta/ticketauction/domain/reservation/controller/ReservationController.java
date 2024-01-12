package com.sparta.ticketauction.domain.reservation.controller;

import static com.sparta.ticketauction.global.response.SuccessCode.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.ticketauction.domain.reservation.request.ReservationCreateRequest;
import com.sparta.ticketauction.domain.reservation.response.ReservationDetailResponse;
import com.sparta.ticketauction.domain.reservation.response.ReservationResponse;
import com.sparta.ticketauction.domain.reservation.service.ReservationService;
import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.global.annotaion.CurrentUser;
import com.sparta.ticketauction.global.exception.ApiException;
import com.sparta.ticketauction.global.exception.ErrorCode;
import com.sparta.ticketauction.global.response.ApiResponse;

import jakarta.validation.Valid;
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
		@Valid @RequestBody ReservationCreateRequest request,
		@CurrentUser User user
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

	@GetMapping
	public ResponseEntity<ApiResponse<List<ReservationResponse>>> searchReservations(
		@RequestParam Integer page,
		@RequestParam Integer size,
		@CurrentUser User user
	) {
		List<ReservationResponse> response =
			reservationService.searchReservations(user, page, size);

		return ResponseEntity.ok(ApiResponse.of(
			SUCCESS_RESERVATIONS_SEARCH.getCode(),
			SUCCESS_RESERVATIONS_SEARCH.getMessage(),
			response
		));
	}
}
