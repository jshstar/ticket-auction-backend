package com.sparta.ticketauction.domain.user;

import java.time.LocalDate;

import com.sparta.ticketauction.domain.user.request.UserCreateRequest;

public class UserUtil {

	public static UserCreateRequest getUserCreateRequest(
		String email,
		String password,
		String name,
		String nickname,
		String phoneNumber,
		LocalDate birth
	) {
		return new UserCreateRequest(
			email,
			password,
			name,
			nickname,
			phoneNumber,
			birth
		);
	}

}
