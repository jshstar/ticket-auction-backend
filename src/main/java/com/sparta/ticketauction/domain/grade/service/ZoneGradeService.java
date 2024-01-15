package com.sparta.ticketauction.domain.grade.service;

import com.sparta.ticketauction.domain.admin.request.ZoneGradeRequest;
import com.sparta.ticketauction.domain.grade.entity.Grade;
import com.sparta.ticketauction.domain.grade.entity.ZoneGrade;
import com.sparta.ticketauction.domain.place.entity.Zone;

public interface ZoneGradeService {

	// 구역 등급 생성
	ZoneGrade createZoneGrade(ZoneGradeRequest zoneGradeRequest, Zone zone, Grade grade);
}
