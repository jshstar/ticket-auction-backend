package com.sparta.ticketauction.domain.place.service;

import java.util.List;

import com.sparta.ticketauction.domain.admin.request.PlaceRequest;
import com.sparta.ticketauction.domain.place.dto.ZoneInfo;
import com.sparta.ticketauction.domain.place.entity.Place;

public interface PlaceService {

	// 공연장 생성
	public Place createPlace(PlaceRequest placeRequest);

	// 공연장 총 좌석 개수 계산
	public Integer calculateSeats(List<ZoneInfo> seats);

}
