package com.sparta.ticketauction.domain.admin.request;

import java.util.List;

import com.sparta.ticketauction.domain.place.dto.ZoneInfo;
import com.sparta.ticketauction.domain.place.entity.Place;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlaceCreateRequest {
	@Size(min = 1, max = 30, message = "공연장 이름은 필수입니다.")
	private final String name;

	@Size(min = 1, max = 150, message = "주소 입력은 필수입니다.")
	private final String address;

	@NotNull(message = "구역 정보 입력은 필수입니다.")
	private final List<ZoneInfo> zoneInfos;

	public Place toEntity(Integer countSeats) {
		return Place
			.builder()
			.name(this.name)
			.address(this.address)
			.countSeats(countSeats)
			.build();

	}
}
