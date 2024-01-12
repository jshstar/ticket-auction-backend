package com.sparta.ticketauction.domain.place.service;

import java.util.List;

import com.sparta.ticketauction.domain.place.dto.ZoneInfo;
import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.place.entity.Zone;

public interface ZoneService {

	// 공연장 구역 생성
	public List<Zone> createZone(Place place, List<ZoneInfo> zoneInfos);
}
