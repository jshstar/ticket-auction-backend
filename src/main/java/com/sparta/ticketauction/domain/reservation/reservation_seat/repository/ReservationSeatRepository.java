package com.sparta.ticketauction.domain.reservation.reservation_seat.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.reservation.reservation_seat.entity.ReservationSeat;
import com.sparta.ticketauction.domain.reservation.reservation_seat.entity.ReservationSeatId;

public interface ReservationSeatRepository
	extends JpaRepository<ReservationSeat, ReservationSeatId>, ReservationSeatQueryRepository {

	@EntityGraph(attributePaths = {"schedule"})
	Slice<ReservationSeat> findByScheduleStartDateTimeGreaterThan(LocalDateTime startDateTime, Pageable pageable);
}
