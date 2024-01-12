package com.sparta.ticketauction.domain.user.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserNicknameUpdateRequest {

	@Pattern(regexp = "^[가-힣]+$", message = "한글로만 입력해주세요.")
	@Size(min = 2, max = 10, message = "최소 2자, 최대 10자로 입력해주세요.")
	private final String nickname;

}
