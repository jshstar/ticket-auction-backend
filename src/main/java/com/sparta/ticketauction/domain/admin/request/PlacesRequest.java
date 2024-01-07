package com.sparta.ticketauction.domain.admin.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlacesRequest {
	@Size(max = 30)
	@NotBlank
	private final String name;

	@Size(max = 150)
	@NotBlank
	private final String address;

	@NotBlank
	private final Integer countSeats;
}
