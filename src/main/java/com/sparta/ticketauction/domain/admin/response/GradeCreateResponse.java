package com.sparta.ticketauction.domain.admin.response;

import lombok.Getter;

@Getter
public class GradeCreateResponse {
	private final Long placeId;

	private final Long gradeId;

	public GradeCreateResponse(Long placeId, Long gradeId) {
		this.placeId = placeId;
		this.gradeId = gradeId;
	}

}
