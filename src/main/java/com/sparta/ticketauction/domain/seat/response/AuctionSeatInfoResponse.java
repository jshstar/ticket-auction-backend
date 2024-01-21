package com.sparta.ticketauction.domain.seat.response;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AuctionSeatInfoResponse {

	private String zoneName;

	private String gradeName;

	private Long price;

	private Long zoneGradeId;

	private Long auctionId;

	private Integer seatNumber;

	private LocalDateTime deadline;

	private Boolean isEnded;
}
