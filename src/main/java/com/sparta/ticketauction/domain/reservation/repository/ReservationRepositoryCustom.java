package com.sparta.ticketauction.domain.reservation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.sparta.ticketauction.domain.reservation.response.ReservationDetailResponse;
import com.sparta.ticketauction.domain.reservation.response.ReservationResponse;

public interface ReservationRepositoryCustom {

	/**
	 * 유저의 예매 기록을 조회한다
	 * @param userId 실행 유저 id
	 * @param pageable 페이지네이션 값 (page, size)
	 * @return 유저의 예매 기록들을 반환한다.
	 */
	List<ReservationResponse> findReservationsByUserId(Long userId, Pageable pageable);

	Optional<ReservationDetailResponse> findReservationById(Long reservationId);
}
