package com.sparta.ticketauction.domain.grade.service;

import org.springframework.stereotype.Service;

import com.sparta.ticketauction.domain.admin.request.ZoneGradeCreateRequest;
import com.sparta.ticketauction.domain.grade.entity.Grade;
import com.sparta.ticketauction.domain.grade.entity.ZoneGrade;
import com.sparta.ticketauction.domain.grade.repository.ZoneGradeRepository;
import com.sparta.ticketauction.domain.place.entity.Zone;
import com.sparta.ticketauction.global.exception.ApiException;
import com.sparta.ticketauction.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZoneGradeServiceImpl implements ZoneGradeService {

	private final ZoneGradeRepository zoneGradeRepository;

	// 구역 등급 생성
	@Override
	public ZoneGrade createZoneGrade(ZoneGradeCreateRequest zoneGradeCreateRequest, Zone zone, Grade grade) {
		ZoneGrade zoneGrade = zoneGradeCreateRequest.toEntity(zone, grade);
		return zoneGradeRepository.save(zoneGrade);
	}

	@Override
	public ZoneGrade findZoneGradeWithFetch(Long id, boolean fetchZone, boolean fetchGrade) {
		return zoneGradeRepository.findZoneGrade(id, fetchZone, fetchGrade)
			.orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_ZONE_GRADE));
	}
}
