package com.sparta.ticketauction.domain.seat.request;

import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.seat.entity.Seat;

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

	public Seat toEntity(Place place) {
		return Seat
			.builder()
			.zone(this.zone)
			.seatNumber(this.seatNumber)
			.place(place)
			.build();
	}

}
