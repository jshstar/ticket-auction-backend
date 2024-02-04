package com.sparta.ticketauction.domain.reservation.reservation_seat.service;

public interface ReservationSeatService {

	/**
	 * 예약된 좌석 정보 캐시 초기 설정
	 */
	void seatCacheWarmUp();
}
