package com.sparta.ticketauction.domain.place.service;

import java.util.List;

import com.sparta.ticketauction.domain.admin.request.PlaceCreateRequest;
import com.sparta.ticketauction.domain.place.dto.ZoneInfo;
import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.place.response.PlaceGetResponse;

public interface PlaceService {

	// 공연장 생성
	Place createPlace(PlaceCreateRequest placeCreateRequest);

	// 공연장 총 좌석 개수 계산
	Integer calculateSeats(List<ZoneInfo> seats);

	// 공연장 프록시 객체 조회 생성
	Place getReferenceById(Long placeId);

	// 공연장 전체 조회
	List<PlaceGetResponse> getAllPlace();

}
