package com.sparta.ticketauction.domain.user.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLoginRequest {

	private String email;
	private String password;

	@Builder
	public UserLoginRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
