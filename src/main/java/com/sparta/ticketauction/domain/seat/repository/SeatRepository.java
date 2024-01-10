package com.sparta.ticketauction.domain.seat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparta.ticketauction.domain.seat.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {

	@Query("select s from Seat s join fetch s.place where s.id = :seatId")
	Optional<Seat> findSeatWithPlaceById(Long seatId);

	List<Seat> findAllByPlaceIdAndZone(Long placeId, String zone);

	Optional<Seat> findByPlaceIdAndZoneAndSeatNumber(Long placeId, String zone, Integer seatNumber);

	List<Seat> findAllByPlaceIdAndZoneAndSeatNumberIn(Long placeId, String zone, List<Integer> seatNumber);
}
