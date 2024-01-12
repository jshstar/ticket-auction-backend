package com.sparta.ticketauction.domain.place.service;

import com.sparta.ticketauction.domain.place.entity.Place;

public interface PlaceService {

	// 공연장 저장
	Place savePlace(Place place);
	
	// 공연장 찾기
	Place findPlace(Long placeId);

}
