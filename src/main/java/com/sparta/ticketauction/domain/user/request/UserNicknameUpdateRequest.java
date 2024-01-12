package com.sparta.ticketauction.domain.user.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserNicknameUpdateRequest {

	@Pattern(regexp = "^[가-힣]+$", message = "한글로만 입력해주세요.")
	@Size(min = 2, max = 10, message = "최소 2자, 최대 10자로 입력해주세요.")
	private String nickname;

}
