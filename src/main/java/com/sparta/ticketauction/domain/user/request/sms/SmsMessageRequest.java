package com.sparta.ticketauction.domain.user.request.sms;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmsMessageRequest {

	private String type;
	private String contentType;
	private String countryCode;
	private String from;
	private List<UserForVerificationRequest> messages;
	private String content;

	@Builder
	private SmsMessageRequest(
		String type,
		String contentType,
		String countryCode,
		String from,
		List<UserForVerificationRequest> messages,
		String content
	) {
		this.type = type;
		this.contentType = contentType;
		this.countryCode = countryCode;
		this.from = from;
		this.content = content;
		this.messages = messages;
	}
}
