package com.sparta.ticketauction.domain.bid.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import com.sparta.ticketauction.domain.bid.constant.BidStatus;

import lombok.Getter;

@Getter
public class BidInfoResponse {
	private final Long id;
	private final Long auctionId;
	private final Long price;
	private final String status;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private final LocalDateTime createdAt;

	@QueryProjection
	public BidInfoResponse(Long id, Long auctionId, Long price, BidStatus status, LocalDateTime createdAt) {
		this.id = id;
		this.auctionId = auctionId;
		this.price = price;
		this.status = status.getKo();
		this.createdAt = createdAt;
	}
}
