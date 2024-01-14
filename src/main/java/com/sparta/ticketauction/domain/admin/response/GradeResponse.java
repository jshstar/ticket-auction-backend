package com.sparta.ticketauction.domain.admin.response;

import lombok.Getter;

@Getter
public class GradeResponse {
	private final Long placeId;

	public GradeResponse(Long placeId) {
		this.placeId = placeId;
	}

}
