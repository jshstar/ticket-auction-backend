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

	public void saveAllSeat(List<Seat> seats) {
		seatRepository.saveAll(seats);
	}
}
