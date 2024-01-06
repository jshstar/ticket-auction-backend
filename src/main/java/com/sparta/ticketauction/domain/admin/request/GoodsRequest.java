package com.sparta.ticketauction.domain.admin.request;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GoodsRequest {
	private final String name;

	private final String description;

	private final LocalDateTime startDate;

	private final LocalDateTime endDate;

	private final int ageGrade;

	private final String runningTime;

	private final String categoryName;

}
