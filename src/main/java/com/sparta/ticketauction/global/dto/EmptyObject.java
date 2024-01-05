package com.sparta.ticketauction.global.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmptyObject {

	private static final EmptyObject emptyObject = new EmptyObject();

	public static EmptyObject get() {
		return emptyObject;
	}
}
