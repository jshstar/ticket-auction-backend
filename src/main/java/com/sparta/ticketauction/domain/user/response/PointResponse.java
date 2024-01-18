package com.sparta.ticketauction.domain.user.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.querydsl.core.annotations.QueryProjection;
import com.sparta.ticketauction.domain.user.enums.PointType;

import lombok.Getter;

@Getter
public class PointResponse {
	private Long id;
	private String time;
	private Long amount;
	private String type;

	@QueryProjection
	public PointResponse(Long id, LocalDateTime createdAt, Long amount, PointType type) {
		this.id = id;
		this.time = createdAt.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
		this.amount = amount;
		this.type = type.getType();
	}
}
