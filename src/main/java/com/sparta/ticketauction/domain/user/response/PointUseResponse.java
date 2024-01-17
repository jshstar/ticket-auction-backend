package com.sparta.ticketauction.domain.user.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class PointUseResponse {
	private Long id;
	private String time;
	private Long amount;

	@QueryProjection
	public PointUseResponse(Long id, LocalDateTime createdAt, Long amount) {
		this.id = id;
		this.time = createdAt.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
		this.amount = amount;
	}
}
