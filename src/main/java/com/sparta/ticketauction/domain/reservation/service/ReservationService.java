package com.sparta.ticketauction.domain.reservation.service;

import java.util.List;

import com.sparta.ticketauction.domain.reservation.request.ReservationCreateRequest;
import com.sparta.ticketauction.domain.reservation.response.ReservationDetailResponse;
import com.sparta.ticketauction.domain.reservation.response.ReservationResponse;
import com.sparta.ticketauction.domain.user.entity.User;

public interface ReservationService {

	/**
	 * 1개 좌석을 예매한다.
	 * @param user 실행 유저
	 * @param seatId 예매할 좌석
	 * @param sequenceId 예매할 공연의 회차
	 * @param request 예매 요청 정보
	 * @return 예매한 좌석의 정보를 반환한다.
	 */
	ReservationDetailResponse reserve(
		User user,
		Long seatId,
		Long sequenceId,
		ReservationCreateRequest request
	);

	/**
	 * 실행 유저의 예매 내역을 조회한다.
	 * @param user 실행 유저
	 * @param page 조회할 페이지
	 * @param size 조회할 페이지 크기
	 * @return 예매한 기록들을 반환한다.
	 */
	List<ReservationResponse> searchReservations(
		User user,
		int page,
		int size
	);

	/**
	 * 예매 상세 정보를 조회한다.
	 * @param user 실행 유저
	 * @param reservationId 예매 id
	 * @return 예매 상세 정보를 반환한다.
	 */
	ReservationDetailResponse searchReservation(User user, Long reservationId);
}
