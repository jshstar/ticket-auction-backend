package com.sparta.ticketauction.domain.admin.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sparta.ticketauction.domain.admin.request.GoodsRequest;
import com.sparta.ticketauction.domain.admin.request.PlaceRequest;
import com.sparta.ticketauction.domain.admin.response.PlaceResponse;

public interface AdminService {

	// 공연장 생성
	List<PlaceResponse> createPlace(PlaceRequest placeRequest);

	// 공연 생성
	void createGoodsAndSequence(GoodsRequest goodsRequest, Long placeId, List<MultipartFile> files);
}
