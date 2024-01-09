package com.sparta.ticketauction.domain.admin.request;

import java.util.List;

import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.seat.request.SeatRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlaceRequest {
	@Size(min = 1, max = 30, message = "공연장 이름은 필수입니다.")
	private final String name;

	@Size(min = 1, max = 150, message = "주소 입력은 필수입니다.")
	private final String address;

	@Valid
	@NotNull(message = "좌석 정보는 필수입니다.")
	private final List<SeatRequest> seats;

	public Place toEntity(Integer countSeats) {
		return Place
			.builder()
			.name(this.name)
			.address(this.address)
			.countSeats(countSeats)
			.build();

	}
}
