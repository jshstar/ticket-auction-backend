package com.sparta.ticketauction.domain.reservation;

import com.sparta.ticketauction.domain.reservation.request.ReservationCreateRequest;

public class ReservationUtil {

	public static final Long TEST_SEAT_ID = 1L;
	public static final Long TEST_SEQUENCE_ID = 1L;
	public static final String TEST_ZONE = "A";
	public static final Long TEST_MONEY = 200000L;
	public static final Long TEST_PRICE = 10000L;
	public static final ReservationCreateRequest TEST_RESERVATION_CREATE_REQUEST =
		new ReservationCreateRequest(TEST_ZONE, TEST_PRICE);
}
