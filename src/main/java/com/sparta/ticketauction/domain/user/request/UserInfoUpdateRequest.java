package com.sparta.ticketauction.domain.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserInfoUpdateRequest {

	@NotBlank
	@Pattern(regexp = "^[가-힣]+$")
	private String nickname;

	@NotBlank
	@Pattern(regexp = "^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$")
	private String phoneNumber;
}
