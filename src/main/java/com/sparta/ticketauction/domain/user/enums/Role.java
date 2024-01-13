package com.sparta.ticketauction.domain.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
	USER("ROLE_USER"),
	ADMIN("ROLE_ADMIN");

	private final String authority;
}
