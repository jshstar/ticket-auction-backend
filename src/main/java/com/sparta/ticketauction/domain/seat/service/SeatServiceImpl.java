package com.sparta.ticketauction.domain.seat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sparta.ticketauction.domain.seat.entity.Seat;
import com.sparta.ticketauction.domain.seat.repository.SeatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

	private final SeatRepository seatRepository;

	// 모든 좌석 저장
	@Override
	public void saveAllSeat(List<Seat> seats) {
		seatRepository.saveAll(seats);
	}

	// 좌석 조회
	@Override
	public Seat findSeat(Long placeId, String zone, Integer seatNumber) {
		return seatRepository.findByPlaceIdAndZoneAndSeatNumber(placeId, zone, seatNumber).orElseThrow();
	}

	// 구역별 좌석 조회
	@Override
	public List<Seat> findAllSeatOfZone(Long placeId, String zone) {
		return seatRepository.findAllByPlaceIdAndZone(placeId, zone);
	}

	// 특정 좌석 리스트 조회
	@Override
	public List<Seat> findAllSeatNumber(Long placeId, String zone, List<Integer> seatNumbers) {
		return seatRepository.findAllByPlaceIdAndZoneAndSeatNumberIn(placeId, zone, seatNumbers);
	}
}
