package com.sparta.ticketauction.domain.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointType {

	USE("사용"),
	REFUND("환불"),
	CHARGE("충전");

	private final String type;
}
