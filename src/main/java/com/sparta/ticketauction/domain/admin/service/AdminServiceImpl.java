package com.sparta.ticketauction.domain.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.ticketauction.domain.admin.request.PlaceRequest;
import com.sparta.ticketauction.domain.admin.response.PlaceResponse;
import com.sparta.ticketauction.domain.goods.service.GoodsService;
import com.sparta.ticketauction.domain.place.dto.ZoneInfo;
import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.place.entity.Zone;
import com.sparta.ticketauction.domain.place.service.PlaceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final PlaceService placeService;

	private final GoodsService goodsService;

	public static final String S3_PATH = "https://auction-ticket.s3.ap-northeast-2.amazonaws.com/";

	public static final String FILE_PATH = "goods/";

	public static final String THUMBNAIL = "thumbnail/";

	public static final String GENERAL = "general/";

	// 공연장 및 구역 생성
	@Override
	@Transactional
	public List<PlaceResponse> createPlaceAndZone(PlaceRequest placeRequest) {
		List<ZoneInfo> zoneInfos = placeRequest.getZoneInfos();
		Place place = placeService.createPlace(placeRequest);
		List<Zone> zone = placeService.createZone(place, zoneInfos);

		return createPlaceResponse(zone);

	}

	// 공연장 및 구역 응답 생성
	@Override
	public List<PlaceResponse> createPlaceResponse(List<Zone> zoneList) {
		List<PlaceResponse> placeResponseList = new ArrayList<>();

		for (Zone zone : zoneList) {
			placeResponseList.add(new PlaceResponse(zone.getName(), zone.getSeatNumber(), zone.getPlace().getId()));
		}

		return placeResponseList;
	}

}
