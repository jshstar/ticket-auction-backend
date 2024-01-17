package com.sparta.ticketauction.domain.admin.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sparta.ticketauction.domain.admin.request.GoodsCreateRequest;
import com.sparta.ticketauction.domain.admin.request.GoodsInfoCreateRequest;
import com.sparta.ticketauction.domain.admin.request.GradeCreateRequest;
import com.sparta.ticketauction.domain.admin.request.PlaceCreateRequest;
import com.sparta.ticketauction.domain.admin.request.ZoneGradeCreateRequest;
import com.sparta.ticketauction.domain.admin.response.GoodsCreateResponse;
import com.sparta.ticketauction.domain.admin.response.GoodsInfoCreateResponse;
import com.sparta.ticketauction.domain.admin.response.GradeCreateResponse;
import com.sparta.ticketauction.domain.admin.response.PlaceCreateResponse;
import com.sparta.ticketauction.domain.admin.response.ZoneGradeCreateResponse;
import com.sparta.ticketauction.domain.auction.request.AuctionCreateRequest;
import com.sparta.ticketauction.domain.place.entity.Zone;

public interface AdminService {

	// 공연장 및 구역 생성
	List<PlaceCreateResponse> createPlaceAndZone(PlaceCreateRequest placeCreateRequest);

	// 공연장 및 구역 응답 생성
	List<PlaceCreateResponse> createPlaceResponse(List<Zone> zoneList);

	//  공연과 관련된 공연 정보, 공연 카테고리, 공연 이미지 생성
	GoodsInfoCreateResponse createGoodsBundle(GoodsInfoCreateRequest goodsInfoCreateRequest,
		List<MultipartFile> multipartFiles);

	// 공연 및 회차 생성
	GoodsCreateResponse createGoodsAndSchedule(GoodsCreateRequest goodsCreateRequest, Long goodsInfoId, Long placeId);

	// 등급 생성
	GradeCreateResponse createGrade(Long goodsId, GradeCreateRequest gradeCreateRequest);

	// 구역 등급 생성
	ZoneGradeCreateResponse createZoneGrade(ZoneGradeCreateRequest zoneGradeCreateRequest);

	// 경매 생성
	void createAuction(Long scheduleId, Long zoneGradeId, AuctionCreateRequest auctionCreateRequest);

}
