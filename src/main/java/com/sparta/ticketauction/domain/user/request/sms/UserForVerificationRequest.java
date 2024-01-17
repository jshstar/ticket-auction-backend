package com.sparta.ticketauction.domain.user.request.sms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserForVerificationRequest {

	@NotBlank(message = "필수 입력입니다.")
	@Pattern(regexp = "^01([0|1|6|7|8|9])?([0-9]{3,4})?([0-9]{4})$", message = "숫자로만 입력해주세요.")
	private String to;
}
