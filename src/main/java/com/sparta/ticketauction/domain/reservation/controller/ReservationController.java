package com.sparta.ticketauction.domain.reservation.controller;

import static com.sparta.ticketauction.global.response.SuccessCode.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.sparta.ticketauction.global.dto.EmptyObject;
import com.sparta.ticketauction.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

	private final ReservationService reservationService;

	@PostMapping
	public ResponseEntity<ApiResponse<ReservationDetailResponse>> reserve(
		@RequestBody ReservationCreateRequest request,
		@CurrentUser User user
	) {
		ReservationDetailResponse response = reservationService.reserve(user, request);

		return ResponseEntity
			.status(SUCCESS_RESERVE.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_RESERVE.getCode(),
					SUCCESS_RESERVE.getMessage(),
					response)
			);
	}

	@GetMapping("/{reservationId}")
	public ResponseEntity<ApiResponse<ReservationDetailResponse>> getReservationDetail(
		@PathVariable Long reservationId,
		@CurrentUser User user
	) {

		ReservationDetailResponse response =
			reservationService.getReservationDetailResponse(user, reservationId);

		return ResponseEntity
			.status(SUCCESS_SEARCH_RESERVATION.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_SEARCH_RESERVATION.getCode(),
					SUCCESS_SEARCH_RESERVATION.getMessage(),
					response
				)
			);
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<ReservationResponse>>> searchReservations(
		@CurrentUser User user,
		@RequestParam Integer page,
		@RequestParam Integer size
	) {
		List<ReservationResponse> response = reservationService.searchReservations(user, page, size);

		return ResponseEntity
			.status(SUCCESS_SEARCH_RESERVATIONS.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_SEARCH_RESERVATIONS.getCode(),
					SUCCESS_SEARCH_RESERVATIONS.getMessage(),
					response
				)
			);
	}

	@DeleteMapping("/{reservationId}")
	public ResponseEntity<ApiResponse<EmptyObject>> cancelReservation(
		@PathVariable Long reservationId,
		@CurrentUser User user
	) {
		reservationService.cancelReservation(user, reservationId);

		return ResponseEntity
			.status(SUCCESS_CANCEL_RESERVATION.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_CANCEL_RESERVATION.getCode(),
					SUCCESS_CANCEL_RESERVATION.getMessage()
				)
			);
	}

	@PostMapping("/{reservationId}/qrcode")
	public ResponseEntity<ApiResponse<String>> createQRCode(
		@PathVariable Long reservationId,
		@CurrentUser User user
	) {
		String qrCode = reservationService.createQRCode(user, reservationId);

		return ResponseEntity
			.status(SUCCESS_CREATE_RESERVATION_AUTHENTICATION_QRCODE.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_CREATE_RESERVATION_AUTHENTICATION_QRCODE.getCode(),
					SUCCESS_CREATE_RESERVATION_AUTHENTICATION_QRCODE.getMessage(),
					qrCode
				)
			);
	}

	@PostMapping("/{reservationId}/auth")
	public ResponseEntity<ApiResponse<EmptyObject>> authenticateReservation(
		@PathVariable Long reservationId,
		@RequestParam String authCode
	) {
		reservationService.authenticateReservation(reservationId, authCode);

		return ResponseEntity
			.status(SUCCESS_AUTHENTICATE_RESERVATION.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_AUTHENTICATE_RESERVATION.getCode(),
					SUCCESS_AUTHENTICATE_RESERVATION.getMessage()
				)
			);
	}
}
