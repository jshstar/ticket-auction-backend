package com.sparta.ticketauction.domain.reservation.reservation_seat.repository;

import static com.sparta.ticketauction.domain.reservation.reservation_seat.entity.QReservationSeat.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.ticketauction.domain.reservation.reservation_seat.entity.ReservationSeat;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReservationSeatQueryRepositoryImpl implements ReservationSeatQueryRepository {

	private JPAQueryFactory query;

	@Override
	public List<ReservationSeat> findReservationSeatsByReservationId(Long reservationId) {
		return query.select(reservationSeat)
			.from(reservationSeat)
			.where(reservationSeat.reservation.id.eq(reservationId))
			.fetch();
	}
}
