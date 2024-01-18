package com.sparta.ticketauction.domain.reservation.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sparta.ticketauction.domain.reservation.response.ReservationDetailResponse;
import com.sparta.ticketauction.domain.reservation.response.ReservationResponse;

public interface ReservationQueryRepository {

	/**
	 * 유저가 회차에 예매한 좌석 수를 반환한다.
	 * @param userId 예매 유저
	 * @param scheduleId 예매한 회차
	 * @return 예매한 좌석 수를 반환한다.
	 */
	Long countReservationSeats(Long userId, Long scheduleId);

	/**
	 * 예약 상세 정보를 반환한다.
	 * @param reservationId 예약 id
	 * @return 예약 상세 정보(ReservationDetailResponse)를 반환한다.
	 */
	Optional<ReservationDetailResponse> getReservationDetailResponse(Long reservationId);

	/**
	 * 유저의 예약 기록을 페이징하여 조회한다
	 * @param userId 유저 id
	 * @param pageable 페이징 정보
	 * @return 예약 기록 정보들을 반환한다.
	 */
	Page<ReservationResponse> getReservationsResponse(Long userId, Pageable pageable);

}
