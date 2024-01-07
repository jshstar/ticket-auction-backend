package com.sparta.ticketauction.domain.user;

import java.time.LocalDate;

import com.sparta.ticketauction.domain.user.request.UserCreateRequest;

public class UserUtil {

	public static final String EMAIL = "tester@gmail.com";
	public static final String PASSWORD = "test123!@#";
	public static final String NAME = "김수한";
	public static final String NICKNAME = "두루미";
	public static final String PHONE_NUMBER = "010-1234-5678";
	public static final LocalDate BIRTH = LocalDate.of(1990, 1, 1);

	public static UserCreateRequest getUserCreateRequest() {
		return new UserCreateRequest(
			EMAIL,
			PASSWORD,
			NAME,
			NICKNAME,
			PHONE_NUMBER,
			BIRTH
		);
	}

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
