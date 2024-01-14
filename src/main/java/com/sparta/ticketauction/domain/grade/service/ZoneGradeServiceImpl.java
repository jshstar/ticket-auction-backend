package com.sparta.ticketauction.domain.grade.service;

import org.springframework.stereotype.Service;

import com.sparta.ticketauction.domain.admin.request.ZoneGradeRequest;
import com.sparta.ticketauction.domain.grade.entity.Grade;
import com.sparta.ticketauction.domain.grade.entity.ZoneGrade;
import com.sparta.ticketauction.domain.grade.repository.ZoneGradeRepository;
import com.sparta.ticketauction.domain.place.entity.Zone;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZoneGradeServiceImpl implements ZoneGradeService {

	private final ZoneGradeRepository zoneGradeRepository;

	// 구역 등급 생성
	@Override
	public ZoneGrade createZoneGrade(ZoneGradeRequest zoneGradeRequest, Zone zone, Grade grade) {
		ZoneGrade zoneGrade = zoneGradeRequest.toEntity(zone, grade);
		return zoneGradeRepository.save(zoneGrade);
	}

}
