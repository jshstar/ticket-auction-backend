package com.sparta.ticketauction.domain.seat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparta.ticketauction.domain.seat.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {

	@Query("select s from Seat s join fetch s.place where s.id = :seatId")
	Optional<Seat> findSeatWithPlaceById(Long seatId);

	// 구역별 모든 좌석 조회
	List<Seat> findAllByPlaceIdAndZone(Long placeId, String zone);

	// 특정 좌석 조회
	Optional<Seat> findByPlaceIdAndZoneAndSeatNumber(Long placeId, String zone, Integer seatNumber);

	// 특정 좌석 리스트 조회
	List<Seat> findAllByPlaceIdAndZoneAndSeatNumberIn(Long placeId, String zone, List<Integer> seatNumber);
}
