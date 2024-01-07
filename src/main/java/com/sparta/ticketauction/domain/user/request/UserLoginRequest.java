package com.sparta.ticketauction.domain.user.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserLoginRequest {

	private final String email;
	private final String password;
}
