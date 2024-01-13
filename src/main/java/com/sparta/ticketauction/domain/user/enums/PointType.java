package com.sparta.ticketauction.domain.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointType {

	USE("USE"),
	CHARGE("CHARGE");

	private final String type;
}
