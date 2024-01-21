package com.sparta.ticketauction.domain.seat.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SeatInfoResponse {

	private String zoneName;

	private String gradeName;

	private Long price;

	private Long zoneGradeId;
}
