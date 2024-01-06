package com.sparta.ticketauction.domain.sequence.request;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class SequenceRequest {

	private int sequence;

	private LocalDateTime startDateTime;

}
