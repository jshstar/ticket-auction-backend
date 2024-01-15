package com.sparta.ticketauction.domain.admin.response;

import lombok.Getter;

@Getter
public class GradeResponse {
	private final Long placeId;

	private final Long gradeId;

	public GradeResponse(Long placeId, Long gradeId) {
		this.placeId = placeId;
		this.gradeId = gradeId;
	}

}
