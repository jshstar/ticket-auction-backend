package com.sparta.ticketauction.domain.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserPhoneUpdateRequest {

	@NotBlank(message = "필수 입력입니다.")
	@Pattern(regexp = "^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$", message = "전화번호 형식으로 입력해주세요.")
	private final String phoneNumber;

	@NotBlank(message = "필수 입력입니다.")
	private final String verificationNumber;
}
