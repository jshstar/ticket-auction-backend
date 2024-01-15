package com.sparta.ticketauction.domain.admin.response;

import com.sparta.ticketauction.domain.grade.entity.Grade;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ZoneGradeResponse {
	private final String gradeName;

	private final Long auctionPrice;

	public ZoneGradeResponse(Grade grade) {
		this.gradeName = grade.getName();
		this.auctionPrice = grade.getAuctionPrice();
	}
}
