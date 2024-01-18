package com.sparta.ticketauction.domain.user.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserUpdateRequest {

	private final String nickname;
	private final String phoneNumber;
	private final String verificationNumber;

}