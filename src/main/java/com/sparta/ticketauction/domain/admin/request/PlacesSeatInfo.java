package com.sparta.ticketauction.domain.admin.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlacesSeatInfo {
	@Size(min = 1, max = 10, message = "필수로 해당 구역을 입력해야 합니다.")
	private final String zone;

	@NotNull(message = "필수로 좌석번호를 입력해야 합니다.")
	private final Integer seatNumber;
}
