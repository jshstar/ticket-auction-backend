package com.sparta.ticketauction.domain.place.service;

import java.util.List;

import com.sparta.ticketauction.domain.place.dto.ZoneInfo;
import com.sparta.ticketauction.domain.place.entity.Zone;
import com.sparta.ticketauction.domain.place.response.ZoneGetResponse;

public interface ZoneService {

	// 공연장 구역 생성
	List<Zone> createZone(List<ZoneInfo> zoneInfos);

	// 구역 프록시 객체 생성
	Zone getReferenceById(Long zoneId);

	// 공연장 구역 전체 조회
	List<ZoneGetResponse> getAllZone(Long placeId);
}
