package com.sparta.ticketauction.domain.schedule.response;

import java.time.LocalDateTime;

import com.sparta.ticketauction.domain.schedule.entity.Schedule;

import lombok.Getter;

@Getter
public class ScheduleGetResponse {
	private final Long scheduleId;

	private final Integer sequence;

	private final LocalDateTime startDateTime;

	public ScheduleGetResponse(Schedule schedule) {
		this.scheduleId = schedule.getId();
		this.sequence = schedule.getSequence();
		this.startDateTime = schedule.getStartDateTime();
	}
}
