package com.sparta.ticketauction.domain.auction.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class AuctionInfoResponse {
	private final Long id;

	private final Boolean isEnded;

	@JsonFormat(pattern = "yyyy.MM.dd")
	private final LocalDateTime startDateTime;

	@QueryProjection
	public AuctionInfoResponse(Long id, Boolean isEnded, LocalDateTime startDateTime) {
		this.id = id;
		this.isEnded = isEnded;
		this.startDateTime = startDateTime;
	}
}
