package com.sparta.ticketauction.domain.admin.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import com.sparta.ticketauction.domain.auction.service.AuctionService;
import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.goods.entity.GoodsCategory;
import com.sparta.ticketauction.domain.goods.entity.GoodsImage;
import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;
import com.sparta.ticketauction.domain.goods.service.GoodsInfoService;
import com.sparta.ticketauction.domain.goods.service.GoodsService;
import com.sparta.ticketauction.domain.grade.entity.Grade;
import com.sparta.ticketauction.domain.grade.entity.ZoneGrade;
import com.sparta.ticketauction.domain.grade.service.GradeService;
import com.sparta.ticketauction.domain.grade.service.ZoneGradeService;
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

	private final ZoneGradeService zoneGradeService;

	private final AuctionService auctionService;

	public static final String S3_PATH = "https://auction-ticket.s3.ap-northeast-2.amazonaws.com/";

	public static final String FILE_PATH = "goods/";

	public static final String THUMBNAIL = "thumbnail/";

	public static final String GENERAL = "general/";

	// 공연장 및 구역 생성
	@Override
	@Transactional
	public List<PlaceCreateResponse> createPlaceAndZone(PlaceCreateRequest placeCreateRequest) {
		List<ZoneInfo> zoneInfos = placeCreateRequest.getZoneInfos();
		Place place = placeService.createPlace(placeCreateRequest);
		List<Zone> zoneList = zoneService.createZone(zoneInfos);
		place.updateZone(zoneList);

		return createPlaceResponse(zoneList);

	}

	// 공연장 및 구역 응답 생성
	@Override
	public List<PlaceCreateResponse> createPlaceResponse(List<Zone> zoneList) {
		List<PlaceCreateResponse> placeCreateResponseList = new ArrayList<>();

		for (Zone zone : zoneList) {
			placeCreateResponseList.add(
				new PlaceCreateResponse(zone.getName(), zone.getSeatNumber(), zone.getPlace().getId()));
		}

		return placeCreateResponseList;
	}

	//  공연과 관련된 공연 정보, 공연 카테고리, 공연 이미지, 공연 및 회차 생성
	@Override
	@Transactional
	public GoodsInfoCreateResponse createGoodsBundle(
		Long placeId,
		GoodsInfoCreateRequest goodsInfoCreateRequest,
		List<MultipartFile> multipartFiles) {

		GoodsInfo goodsInfo = goodsInfoService.createGoodsInfo(goodsInfoCreateRequest);

		List<GoodsImage> goodsImages = goodsInfoService.createGoodsImage(multipartFiles, goodsInfo);
		goodsInfo.addGoodsImage(goodsImages);

		GoodsCategory goodsCategory = goodsInfoService.createGoodsCategory(goodsInfoCreateRequest.getCategoryName());
		goodsInfo.updateGoodsCategory(goodsCategory);

		return new GoodsInfoCreateResponse(goodsInfo.getId());

	}

	// 공연 및 회차 생성
	@Override
	@Transactional
	public GoodsCreateResponse createGoodsAndSchedule(
		GoodsCreateRequest goodsCreateRequest,
		Long goodsInfoId,
		Long placeId) {

		Place place = placeService.getReferenceById(placeId);

		GoodsInfo goodsInfo = goodsInfoService.findByGoodsInfoId(goodsInfoId);

		Goods goods = goodsService.createGoods(goodsCreateRequest, place, goodsInfo);
		goodsInfo.addGoods(goods);

		LocalTime startTime = goodsCreateRequest.getStartTime();

		scheduleService.createSchedule(goods, startTime);

		return new GoodsCreateResponse(goods.getId());
	}

	// 구역 생성
	@Override
	@Transactional
	public GradeCreateResponse createGrade(Long goodsId, GradeCreateRequest gradeCreateRequest) {
		Goods goods = goodsService.findById(goodsId);

		Grade grade = gradeService.createGrade(gradeCreateRequest, goods);

		return new GradeCreateResponse(goods.getPlace().getId(), grade.getId());
	}

	// 구역 등급 생성
	@Override
	@Transactional
	public ZoneGradeCreateResponse createZoneGrade(ZoneGradeCreateRequest zoneGradeCreateRequest) {
		Zone zone = zoneService.getReferenceById(zoneGradeCreateRequest.getZoneId());
		Grade grade = gradeService.getReferenceById(zoneGradeCreateRequest.getGradeId());

		ZoneGrade zoneGrade = zoneGradeService.createZoneGrade(zoneGradeCreateRequest, zone, grade);

		return new ZoneGradeCreateResponse(zoneGrade);
	}

	// 경매 생성
	@Override
	@Transactional
	public void createAuction(Long scheduleId, Long zoneGradeId, AuctionCreateRequest auctionCreateRequest) {
		auctionService.createAuction(scheduleId, zoneGradeId, auctionCreateRequest);
	}
}
