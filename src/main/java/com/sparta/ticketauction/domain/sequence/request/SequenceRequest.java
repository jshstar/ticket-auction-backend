package com.sparta.ticketauction.domain.sequence.request;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SequenceRequest {
	private final int sequence;

	private final LocalDateTime startDateTime;

}
