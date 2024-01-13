package com.sparta.ticketauction.domain.user.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPasswordUpdateRequest {

	@Pattern(
		regexp = "^(?=.*?[a-zA-Z])(?=.*?[0-9])(?=.*?[#!@$ %^])[a-zA-Z0-9@#$%^!]*$",
		message = "영어, 숫자, 특수문자(!,@,#,$,%,^) 조합으로 입력해주세요."
	)
	@Size(min = 8, max = 15, message = "최소 8자, 최대 15자로 입력해주세요.")
	private String password;

	@Builder
	private UserPasswordUpdateRequest(String password) {
		this.password = password;
	}
}
