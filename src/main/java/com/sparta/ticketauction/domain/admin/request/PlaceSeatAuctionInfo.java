package com.sparta.ticketauction.domain.admin.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlaceSeatAuctionInfo {
	@Size(min = 1, max = 10, message = "구역 입력은 필수입니다.")
	private final String zone;

	@NotNull(message = "좌석 번호는 필수입니다.")
	private final Integer seatNumber;
}
