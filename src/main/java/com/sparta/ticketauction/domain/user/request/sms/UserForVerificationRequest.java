package com.sparta.ticketauction.domain.user.request.sms;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserForVerificationRequest {

	private String to;

	@Builder
	private UserForVerificationRequest(String to) {
		this.to = to;
	}
}
