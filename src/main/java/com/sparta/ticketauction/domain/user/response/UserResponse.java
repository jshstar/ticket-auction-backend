package com.sparta.ticketauction.domain.user.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponse {

	private Long id;

	private String email;

	private String name;

	private String nickname;

	private String phoneNumber;
	
	private Long point;
}
