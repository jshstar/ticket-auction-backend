package com.sparta.ticketauction.domain.seat.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SeatRequest {
	@NotBlank
	private final String zone;

	@NotBlank
	private final int seatNumber;

}
