package com.sparta.ticketauction.domain.reservation.reservation_seat.repository;

import static com.sparta.ticketauction.domain.reservation.reservation_seat.entity.QReservationSeat.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.ticketauction.domain.reservation.reservation_seat.entity.ReservationSeat;
import com.sparta.ticketauction.domain.seat.response.ReservedSeatResponse;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReservationSeatQueryRepositoryImpl implements ReservationSeatQueryRepository {

	private final JPAQueryFactory query;

	@Override
	public List<ReservationSeat> findReservationSeatsByReservationId(Long reservationId) {
		return query.select(reservationSeat)
			.from(reservationSeat)
			.where(reservationSeat.reservation.id.eq(reservationId))
			.fetch();
	}

	@Override
	public List<ReservedSeatResponse> findReservedSeats(Long scheduleId) {
		// TODO: redis를 조회하는 것으로 개선해야함.
		List<ReservedSeatResponse> result = query
			.select(Projections.constructor(
				ReservedSeatResponse.class,
				reservationSeat.id.zoneGradeId,
				reservationSeat.id.seatNumber
			))
			.from(reservationSeat)
			.where(reservationSeat.id.scheduleId.eq(scheduleId))
			.fetch();
		return result;
	}
}
