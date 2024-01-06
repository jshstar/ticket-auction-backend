package com.sparta.ticketauction.domain.admin.request;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class GoodsRequest {
	private String name;

	private String description;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	private int ageGrade;

	private String runningTime;

	private String categoryName;

}
