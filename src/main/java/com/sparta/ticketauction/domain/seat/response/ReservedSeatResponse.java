package com.sparta.ticketauction.domain.seat.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ReservedSeatResponse {

	private Long zoneGradeId;

	private Integer seatNumber;
}
