package com.sparta.ticketauction.domain.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserInfoUpdateRequest {

	@NotBlank
	@Pattern(regexp = "^[가-힣]+$")
	private final String nickname;

	@NotBlank
	@Pattern(regexp = "^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$")
	private final String phoneNumber;
}
