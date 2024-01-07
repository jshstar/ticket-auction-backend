package com.sparta.ticketauction.domain.seat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.seat.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
