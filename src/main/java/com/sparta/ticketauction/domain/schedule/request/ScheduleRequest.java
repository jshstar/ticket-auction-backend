package com.sparta.ticketauction.domain.schedule.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.schedule.entity.Schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ScheduleRequest {
	@NotNull(message = "회차 정보는 필수입니다.")
	private final Integer sequence;

	@NotBlank(message = "공연일시 정보는 필수입니다.")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private final LocalDateTime startDateTime;

	public Schedule toEntity(Goods goods) {
		return Schedule
			.builder()
			.sequence(this.sequence)
			.startDateTime(startDateTime)
			.goods(goods)
			.build();
	}

}
