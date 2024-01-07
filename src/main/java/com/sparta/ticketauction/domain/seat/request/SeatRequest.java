package com.sparta.ticketauction.domain.seat.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SeatRequest {
	@NotBlank
	private final String zone;

	@NotNull
	private final Integer seatNumber;

}
