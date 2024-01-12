package com.sparta.ticketauction.domain.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.reservation.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom {

	boolean existsReservationByIdAndUser_Id(Long reservationId, Long userId);
}
