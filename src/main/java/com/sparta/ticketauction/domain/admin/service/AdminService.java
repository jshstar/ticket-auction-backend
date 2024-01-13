package com.sparta.ticketauction.domain.admin.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sparta.ticketauction.domain.admin.request.GoodsRequest;
import com.sparta.ticketauction.domain.admin.request.PlaceRequest;
import com.sparta.ticketauction.domain.admin.response.PlaceResponse;
import com.sparta.ticketauction.domain.place.entity.Zone;

public interface AdminService {

	// 공연장 및 구역 생성
	List<PlaceResponse> createPlaceAndZone(PlaceRequest placeRequest);

	// 공연장 및 구역 응답 생성
	List<PlaceResponse> createPlaceResponse(List<Zone> zoneList);

	//  공연과 관련된 공연 정보, 공연 카테고리, 공연 이미지, 공연 및 회차 생성
	void createGoodsBundleAndSchedule(Long placeId, GoodsRequest goodsRequest, List<MultipartFile> multipartFiles);
}
