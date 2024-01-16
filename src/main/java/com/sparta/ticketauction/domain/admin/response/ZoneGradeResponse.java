package com.sparta.ticketauction.domain.admin.response;

import com.sparta.ticketauction.domain.grade.entity.ZoneGrade;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ZoneGradeResponse {
	private final String gradeName;

	private final Long auctionPrice;

	private final Long zoneGradeId;

	private final String zoneName;

	public ZoneGradeResponse(ZoneGrade zoneGrade) {
		this.gradeName = zoneGrade.getGrade().getName();
		this.auctionPrice = zoneGrade.getGrade().getAuctionPrice();
		this.zoneGradeId = zoneGrade.getId();
		this.zoneName = zoneGrade.getZone().getName();
	}
}
