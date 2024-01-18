package com.sparta.ticketauction.domain.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReservationSeatInfo {

	private String zone;

	private Integer seatNumber;
}
