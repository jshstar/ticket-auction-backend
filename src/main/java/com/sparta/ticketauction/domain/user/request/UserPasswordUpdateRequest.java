package com.sparta.ticketauction.domain.user.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserPasswordUpdateRequest {

	@Size(min = 8, max = 15, message = "최소 8자, 최대 15자로 입력해주세요.")
	private final String password;
}
