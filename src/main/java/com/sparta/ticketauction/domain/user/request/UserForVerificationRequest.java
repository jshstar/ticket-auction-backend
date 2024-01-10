package com.sparta.ticketauction.domain.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserForVerificationRequest {
	private String to;
}
