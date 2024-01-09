package com.sparta.ticketauction.domain.admin.service;

import java.util.List;

import com.sparta.ticketauction.domain.admin.request.PlaceRequest;
import com.sparta.ticketauction.domain.admin.response.PlaceResponse;

public interface AdminService {

	// 공연장 생성
	List<PlaceResponse> createPlace(PlaceRequest placeRequest);
}
