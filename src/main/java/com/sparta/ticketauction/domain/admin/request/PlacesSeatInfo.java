package com.sparta.ticketauction.domain.admin.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlacesSeatInfo {
	@Size(min = 1, max = 10)
	private final String zone;

	@NotBlank
	private final Integer seatNumber;
}
