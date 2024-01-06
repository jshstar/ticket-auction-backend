package com.sparta.ticketauction.domain.user.response;

import lombok.Getter;

@Getter
public class UserResponse {

	private Long id;

	private String email;

	private String name;

	private String nickname;

	private String phoneNumber;

	private long point;
}
