package com.sparta.ticketauction.domain.user.response;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;
import com.sparta.ticketauction.domain.user.enums.PointType;

import lombok.Getter;

@Getter
public class PointResponse {
	private Long id;
	private LocalDateTime time;
	private Long amount;
	private String type;

	@QueryProjection
	public PointResponse(Long id, LocalDateTime createdAt, Long amount, PointType type) {
		this.id = id;
		this.time = createdAt;
		this.amount = amount;
		this.type = type.getType();
	}
}
