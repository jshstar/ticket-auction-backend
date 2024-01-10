package com.sparta.ticketauction.domain.user.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmsResponse {
	private String requestId;
	private LocalDateTime requestTime;
	private String statusCode;
	private String statusName;
	@Setter
	private String verificationNumbers;

}