package com.sparta.ticketauction.domain.reservation.reservation_seat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.reservation.reservation_seat.entity.ReservationSeat;
import com.sparta.ticketauction.domain.reservation.reservation_seat.entity.ReservationSeatId;

public interface ReservationSeatRepository
	extends JpaRepository<ReservationSeat, ReservationSeatId>, ReservationSeatQueryRepository {
}
