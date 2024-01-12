package com.sparta.ticketauction.domain.user.response;

import java.time.LocalDate;

import com.sparta.ticketauction.domain.user.entity.User;

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

	private LocalDate birth;

	private Long point;

	public static UserResponse from(User user) {
		return UserResponse.builder()
			.id(user.getId())
			.email(user.getEmail())
			.name(user.getName())
			.nickname(user.getNickname())
			.phoneNumber(user.getPhoneNumber())
			.birth(user.getBirth())
			.point(user.getPoint())
			.build();
	}
}
