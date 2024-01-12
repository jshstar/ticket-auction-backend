package com.sparta.ticketauction.domain.place.service;

import java.util.List;

import com.sparta.ticketauction.domain.admin.request.PlaceRequest;
import com.sparta.ticketauction.domain.place.dto.ZoneInfo;
import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.place.entity.Zone;

public interface PlaceService {

	// 공연장 생성
	public Place createPlace(PlaceRequest placeRequest);

	// 공연장 총 좌석 개수 계산
	public Integer calculateSeats(List<ZoneInfo> seats);

	// 공연장 구역 생성
	public List<Zone> createZone(Place place, List<ZoneInfo> zoneInfos);

}
