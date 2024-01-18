package com.sparta.ticketauction.domain.reservation.reservation_seat.repository;

import java.util.List;

import com.sparta.ticketauction.domain.reservation.reservation_seat.entity.ReservationSeat;

public interface ReservationSeatQueryRepository {

	/**
	 * 예약에 포함된 예약 좌석들의 목록을 조회한다.
	 * @param reservationId
	 * @return List<ReservationSeatId>
	 */
	List<ReservationSeat> findReservationSeatsByReservationId(Long reservationId);
}
