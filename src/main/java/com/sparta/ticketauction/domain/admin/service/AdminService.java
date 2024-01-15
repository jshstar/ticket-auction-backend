package com.sparta.ticketauction.domain.admin.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sparta.ticketauction.domain.admin.request.GoodsRequest;
import com.sparta.ticketauction.domain.admin.request.GradeRequest;
import com.sparta.ticketauction.domain.admin.request.PlaceRequest;
import com.sparta.ticketauction.domain.admin.request.ZoneGradeRequest;
import com.sparta.ticketauction.domain.admin.response.GoodsResponse;
import com.sparta.ticketauction.domain.admin.response.GradeResponse;
import com.sparta.ticketauction.domain.admin.response.PlaceResponse;
import com.sparta.ticketauction.domain.admin.response.ZoneGradeResponse;
import com.sparta.ticketauction.domain.auction.request.AuctionCreateRequest;
import com.sparta.ticketauction.domain.place.entity.Zone;

public interface AdminService {

	// 공연장 및 구역 생성
	List<PlaceResponse> createPlaceAndZone(PlaceRequest placeRequest);

	// 공연장 및 구역 응답 생성
	List<PlaceResponse> createPlaceResponse(List<Zone> zoneList);

	//  공연과 관련된 공연 정보, 공연 카테고리, 공연 이미지, 공연 및 회차 생성
	GoodsResponse createGoodsBundleAndSchedule(Long placeId, GoodsRequest goodsRequest,
		List<MultipartFile> multipartFiles);

	// 등급 생성
	GradeResponse createGrade(Long goodsId, GradeRequest gradeRequest);

	// 구역 등급 생성
	ZoneGradeResponse createZoneGrade(ZoneGradeRequest zoneGradeRequest);

	// 경매 생성
	void createAuction(Long scheduleId, Long zoneGradeId, AuctionCreateRequest auctionCreateRequest);

}
