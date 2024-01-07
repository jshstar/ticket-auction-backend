package com.sparta.ticketauction.domain.user.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserResponse {

	private final Long id;

	private final String email;

	private final String name;

	private final String nickname;

	private final String phoneNumber;

	private final long point;
}
