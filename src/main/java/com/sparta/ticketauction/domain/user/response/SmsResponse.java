package com.sparta.ticketauction.domain.user.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class SmsResponse {
	private final String requestId;
	private final LocalDateTime requestTime;
	private final String statusCode;
	private final String statusName;
	@Setter
	private String verificationNumbers;

}