package com.sparta.ticketauction.domain.admin.service;

import java.util.List;

import com.sparta.ticketauction.domain.admin.request.PlaceRequest;
import com.sparta.ticketauction.domain.admin.response.PlaceResponse;
import com.sparta.ticketauction.domain.place.entity.Zone;

public interface AdminService {

	// 공연장 및 구역 생성
	List<PlaceResponse> createPlaceAndZone(PlaceRequest placeRequest);

	// 공연장 및 구역 응답 생성
	List<PlaceResponse> createPlaceResponse(List<Zone> zoneList);

}
