package com.sparta.ticketauction.domain.admin.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlacesRequest {
	@Size(min = 1, max = 30)
	private final String name;

	@Size(min = 1, max = 150)
	private final String address;

	@NotBlank
	private final Integer countSeats;
}
