package com.sparta.ticketauction.domain.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointType {

	USE_BIDDING("입찰"),
	USE_PURCHASE("일반 예매"),
	REFUND_BIDDING("낙찰 실패 환불"),
	CHARGE("충전");

	private final String type;
}
