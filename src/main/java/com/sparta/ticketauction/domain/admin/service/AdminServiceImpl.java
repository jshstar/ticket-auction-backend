package com.sparta.ticketauction.domain.admin.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sparta.ticketauction.domain.admin.request.GoodsRequest;
import com.sparta.ticketauction.domain.admin.request.GradeRequest;
import com.sparta.ticketauction.domain.admin.request.PlaceRequest;
import com.sparta.ticketauction.domain.admin.response.GoodsResponse;
import com.sparta.ticketauction.domain.admin.response.GradeResponse;
import com.sparta.ticketauction.domain.admin.response.PlaceResponse;
import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.goods.entity.GoodsCategory;
import com.sparta.ticketauction.domain.goods.entity.GoodsImage;
import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;
import com.sparta.ticketauction.domain.goods.service.GoodsInfoService;
import com.sparta.ticketauction.domain.goods.service.GoodsService;
import com.sparta.ticketauction.domain.grade.service.GradeService;
import com.sparta.ticketauction.domain.place.dto.ZoneInfo;
import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.place.entity.Zone;
import com.sparta.ticketauction.domain.place.service.PlaceService;
import com.sparta.ticketauction.domain.place.service.ZoneService;
import com.sparta.ticketauction.domain.schedule.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final PlaceService placeService;

	private final GoodsService goodsService;

	private final ZoneService zoneService;

	private final GoodsInfoService goodsInfoService;

	private final ScheduleService scheduleService;

	private final GradeService gradeService;

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
		List<Zone> zone = zoneService.createZone(place, zoneInfos);

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

	//  공연과 관련된 공연 정보, 공연 카테고리, 공연 이미지, 공연 및 회차 생성
	@Override
	@Transactional
	public GoodsResponse createGoodsBundleAndSchedule(
		Long placeId,
		GoodsRequest goodsRequest,
		List<MultipartFile> multipartFiles) {

		Place place = placeService.getReferenceById(placeId);

		GoodsInfo goodsInfo = goodsInfoService.createGoodsInfo(goodsRequest);

		List<GoodsImage> goodsImages = goodsInfoService.createGoodsImage(multipartFiles, goodsInfo);
		goodsInfo.addGoodsImage(goodsImages);

		GoodsCategory goodsCategory = goodsInfoService.createGoodsCategory(goodsRequest.getCategoryName());
		goodsInfo.updateGoodsCategory(goodsCategory);

		Goods goods = goodsService.createGoods(goodsRequest, place, goodsInfo);

		LocalTime startTime = goodsRequest.getStartTime();
		scheduleService.createSchedule(goods, startTime);

		return new GoodsResponse(goods.getId());

	}

	// 구역 생성
	@Override
	@Transactional
	public GradeResponse createGrade(Long goodsId, GradeRequest gradeRequest) {
		Goods goods = goodsService.findById(goodsId);
		gradeService.createGrade(gradeRequest, goods);
		return new GradeResponse(goods.getPlace().getId());
	}

}
