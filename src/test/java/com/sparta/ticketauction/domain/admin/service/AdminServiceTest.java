package com.sparta.ticketauction.domain.admin.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sparta.ticketauction.domain.admin.request.GoodsCreateRequest;
import com.sparta.ticketauction.domain.admin.request.GoodsInfoCreateRequest;
import com.sparta.ticketauction.domain.admin.request.GradeCreateRequest;
import com.sparta.ticketauction.domain.admin.request.PlaceCreateRequest;
import com.sparta.ticketauction.domain.admin.request.ZoneGradeCreateRequest;
import com.sparta.ticketauction.domain.admin.response.PlaceCreateResponse;
import com.sparta.ticketauction.domain.admin.response.ZoneGradeCreateResponse;
import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.goods.entity.GoodsCategory;
import com.sparta.ticketauction.domain.goods.entity.GoodsImage;
import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;
import com.sparta.ticketauction.domain.goods.service.GoodsInfoServiceImpl;
import com.sparta.ticketauction.domain.goods.service.GoodsServiceImpl;
import com.sparta.ticketauction.domain.grade.entity.Grade;
import com.sparta.ticketauction.domain.grade.entity.ZoneGrade;
import com.sparta.ticketauction.domain.grade.service.GradeServiceImpl;
import com.sparta.ticketauction.domain.grade.service.ZoneGradeServiceImpl;
import com.sparta.ticketauction.domain.place.dto.ZoneInfo;
import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.place.entity.Zone;
import com.sparta.ticketauction.domain.place.service.PlaceServiceImpl;
import com.sparta.ticketauction.domain.place.service.ZoneServiceImpl;
import com.sparta.ticketauction.domain.schedule.service.ScheduleServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

	@InjectMocks
	AdminServiceImpl adminService;

	@Mock
	PlaceServiceImpl placeService;

	@Mock
	ZoneServiceImpl zoneService;

	@Mock
	GoodsInfoServiceImpl goodsInfoService;

	@Mock
	GoodsServiceImpl goodsService;

	@Mock
	ScheduleServiceImpl scheduleService;

	@Mock
	GradeServiceImpl gradeService;

	@Mock
	ZoneGradeServiceImpl zoneGradeService;

	public static PlaceCreateRequest placeCreateRequest;

	public static GoodsInfoCreateRequest goodsInfoCreateRequest;

	public static GradeCreateRequest gradeCreateRequest;

	public static ZoneGradeCreateRequest zoneGradeCreateRequest;

	public static GoodsCreateRequest goodsCreateRequest;

	@BeforeEach
	public void initPlaceRequest() {
		List<ZoneInfo> zoneInfos = new ArrayList<>();
		zoneInfos.add(new ZoneInfo("A", 100));
		zoneInfos.add(new ZoneInfo("B", 100));
		placeCreateRequest = new PlaceCreateRequest("공연장", "Address", zoneInfos);
		goodsInfoCreateRequest = new GoodsInfoCreateRequest(
			"강아지 공연",
			"강아지 쇼케이스",
			12,
			360,
			"공연"
		);
		goodsCreateRequest = new GoodsCreateRequest(
			"강아지 공연-서울",
			LocalDate.of(2023, 3, 1),
			LocalDate.of(2023, 3, 2),
			LocalTime.of(15, 0));
		gradeCreateRequest = new GradeCreateRequest("VIP", 100000L, 70000L);
		zoneGradeCreateRequest = new ZoneGradeCreateRequest(1L, 1L);
	}

	@Test
	void 공연장_생성_테스트() {
		// given
		Place place = placeCreateRequest.toEntity(100);
		List<Zone> zoneList = new ArrayList<>();
		zoneList.add(
			Zone
				.builder()
				.name(
					placeCreateRequest.getZoneInfos().get(0).getZone())
				.seatNumber(
					placeCreateRequest.getZoneInfos().get(0).getSeatNumber())
				.build()
		);
		zoneList.add(Zone
			.builder()
			.name(
				placeCreateRequest.getZoneInfos().get(1).getZone())
			.seatNumber(
				placeCreateRequest.getZoneInfos().get(1).getSeatNumber())
			.build()
		);
		place.updateZone(zoneList);

		//when
		given(placeService.createPlace(any())).willReturn(place);
		given(zoneService.createZone(any())).willReturn(zoneList);
		List<PlaceCreateResponse> response = adminService.createPlaceAndZone(placeCreateRequest);

		//then
		assertEquals("공연장", place.getName());
		assertEquals("Address", place.getAddress());
		assertEquals(response.get(0).getZone(), zoneList.get(0).getName());
		assertEquals(response.get(0).getZoneCountSeat(), zoneList.get(0).getSeatNumber());
		assertEquals(response.get(1).getZone(), zoneList.get(1).getName());
		assertEquals(response.get(1).getZoneCountSeat(), zoneList.get(1).getSeatNumber());

	}

	@Test
	void 공연정보_공연이미지_공연카테고리() {
		// given
		GoodsInfo goodsInfo = goodsInfoCreateRequest.toEntity();

		List<String> fileUrl = new ArrayList<>();
		fileUrl.add("goods/thumbnail/1/51579925-f563-4c75-9999-e2264dadbdab");
		fileUrl.add("goods/general/1/0aebcd4f-b2b5-4bbc-b8f8-a10c4b8f2c17");

		List<GoodsImage> goodsImage = new ArrayList<>();
		goodsImage.add(GoodsImage.builder().s3Key(fileUrl.get(0)).type("대표").goodsInfo(goodsInfo).build());
		goodsImage.add(GoodsImage.builder().s3Key(fileUrl.get(1)).type("일반").goodsInfo(goodsInfo).build());

		goodsInfo.addGoodsImage(goodsImage);
		GoodsCategory goodsCategory = GoodsCategory.builder().name("공연").build();
		goodsInfo.updateGoodsCategory(goodsCategory);

		// when
		given(goodsInfoService.createGoodsInfo(any(GoodsInfoCreateRequest.class))).willReturn(goodsInfo);
		given(goodsInfoService.createGoodsImage(any(), any())).willReturn(goodsImage);
		given(goodsInfoService.createGoodsCategory(any())).willReturn(goodsCategory);
		adminService.createGoodsBundle(goodsInfoCreateRequest, mock());

		// then
		verify(goodsInfoService, times(1)).createGoodsInfo(any(GoodsInfoCreateRequest.class));
		verify(goodsInfoService, times(1)).createGoodsImage(any(), any(GoodsInfo.class));
		verify(goodsInfoService, times(1)).createGoodsCategory(any());

		assertEquals(goodsImage.get(0).getS3Key(), goodsInfo.getGoodsImage().get(0).getS3Key());
		assertEquals(goodsImage.get(1).getS3Key(), goodsInfo.getGoodsImage().get(1).getS3Key());
		assertEquals(goodsImage.get(0).getType(), goodsInfo.getGoodsImage().get(0).getType());
		assertEquals(goodsImage.get(1).getType(), goodsInfo.getGoodsImage().get(1).getType());
		assertEquals(goodsCategory.getName(), goodsInfo.getGoodsCategory().getName());

	}

	@Test
	void 등급_생성_테스트() {
		// given
		Place place = placeCreateRequest.toEntity(200);
		GoodsInfo goodsInfo = goodsInfoCreateRequest.toEntity();
		Goods goods = goodsCreateRequest.toEntity(place, goodsInfo);
		Grade grade = gradeCreateRequest.toEntity(goods);

		// when
		given(goodsService.findById(any())).willReturn(goods);
		given(gradeService.createGrade(any(GradeCreateRequest.class), any(Goods.class))).willReturn(grade);
		adminService.createGrade(1L, gradeCreateRequest);

		// then
		verify(goodsService, times(1)).findById(any());
		verify(gradeService, times(1)).createGrade(any(GradeCreateRequest.class), any(Goods.class));
		assertEquals(gradeCreateRequest.getName(), grade.getName());
		assertEquals(gradeCreateRequest.getNormalPrice(), grade.getNormalPrice());
		assertEquals(gradeCreateRequest.getAuctionPrice(), grade.getAuctionPrice());
		assertEquals(grade.getGoods(), goods);
		assertEquals(grade.getGoods().getGoodsInfo(), goodsInfo);
	}

	@Test
	void 등급_구역_생성_테스트() {
		// given
		Place place = placeCreateRequest.toEntity(100);
		GoodsInfo goodsInfo = goodsInfoCreateRequest.toEntity();
		Goods goods = goodsCreateRequest.toEntity(place, goodsInfo);
		Grade grade = gradeCreateRequest.toEntity(goods);
		Zone zone =
			Zone
				.builder()
				.name("A")
				.seatNumber(100)
				.build();
		place.updateZone(List.of(zone));
		ZoneGrade zoneGrade = zoneGradeCreateRequest.toEntity(zone, grade);

		// when
		given(zoneService.getReferenceById(anyLong())).willReturn(zone);
		given(gradeService.getReferenceById(anyLong())).willReturn(grade);
		given(
			zoneGradeService
				.createZoneGrade(
					any(ZoneGradeCreateRequest.class),
					any(Zone.class),
					any(Grade.class)))
			.willReturn(zoneGrade);

		ZoneGradeCreateResponse zoneGradeCreateResponse = adminService.createZoneGrade(zoneGradeCreateRequest);

		// then
		verify(zoneService, times(1)).getReferenceById(anyLong());
		verify(gradeService, times(1)).getReferenceById(anyLong());
		verify(zoneGradeService, times(1))
			.createZoneGrade(
				any(ZoneGradeCreateRequest.class),
				any(Zone.class),
				any(Grade.class)
			);
		assertEquals(zoneGrade.getGrade(), grade);
		assertEquals(zoneGrade.getZone(), zone);
		assertEquals(zoneGradeCreateResponse.getGradeName(), zoneGrade.getGrade().getName());
		assertEquals(zoneGradeCreateResponse.getAuctionPrice(), zoneGrade.getGrade().getAuctionPrice());
	}
}
