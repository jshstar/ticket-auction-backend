package com.sparta.ticketauction.domain.place.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ZoneInfo {

	@NotBlank(message = "구역 입력은 필수 입니다.")
	private final String zone;

	@NotNull(message = "구역당 총 좌석입력은 필 수 입니다.")
	private final Integer seatNumber;

}
