package com.sparta.ticketauction.domain.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.reservation.entity.Reservation;
import com.sparta.ticketauction.global.exception.ApiException;
import com.sparta.ticketauction.global.exception.ErrorCode;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationQueryRepository {

	default Reservation getById(Long id) {
		return findById(id).orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_RESERVATION));
	}
}
