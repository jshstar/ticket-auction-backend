package com.sparta.ticketauction.domain.user.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class PointChargeResponse {
	private Long id;
	@JsonFormat(pattern = "yyyy.MM.dd HH:mm")
	private LocalDateTime time;
	private Long amount;
	private String orderId;

	@QueryProjection
	public PointChargeResponse(Long id, LocalDateTime createdAt, Long amount, String orderId) {
		this.id = id;
		this.time = createdAt;
		this.amount = amount;
		this.orderId = orderId;
	}
}
