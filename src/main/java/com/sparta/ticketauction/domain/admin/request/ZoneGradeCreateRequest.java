package com.sparta.ticketauction.domain.admin.request;

import com.sparta.ticketauction.domain.grade.entity.Grade;
import com.sparta.ticketauction.domain.grade.entity.ZoneGrade;
import com.sparta.ticketauction.domain.place.entity.Zone;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ZoneGradeCreateRequest {
	@NotNull(message = "구역 Id값은 필수입니다.")
	private final Long zoneId;

	@NotNull(message = "등급 Id값은 필수입니다.")
	private final Long gradeId;

	public ZoneGrade toEntity(Zone zone, Grade grade) {
		return ZoneGrade
			.builder()
			.zone(zone)
			.grade(grade)
			.build();
	}
}
