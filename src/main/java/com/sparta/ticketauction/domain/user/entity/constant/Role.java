package com.sparta.ticketauction.domain.user.entity.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
	USER("ROLE__USER"),
	ADMIN("ROLE_ADMIN");

	private final String authority;
}
