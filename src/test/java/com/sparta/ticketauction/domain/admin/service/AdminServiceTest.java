package com.sparta.ticketauction.domain.admin.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sparta.ticketauction.domain.admin.request.GoodsRequest;
import com.sparta.ticketauction.domain.admin.request.PlaceRequest;
import com.sparta.ticketauction.domain.admin.response.PlaceResponse;
import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.goods.entity.GoodsCategory;
import com.sparta.ticketauction.domain.goods.entity.GoodsImage;
import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;
import com.sparta.ticketauction.domain.goods.service.GoodsInfoServiceImpl;
import com.sparta.ticketauction.domain.goods.service.GoodsServiceImpl;
import com.sparta.ticketauction.domain.place.dto.ZoneInfo;
import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.place.entity.Zone;
import com.sparta.ticketauction.domain.place.service.PlaceServiceImpl;
import com.sparta.ticketauction.domain.place.service.ZoneServiceImpl;
import com.sparta.ticketauction.domain.schedule.entity.Schedule;
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

	public static PlaceRequest placeRequest;

	public static GoodsRequest goodsRequest;

	@BeforeEach
	public void initPlaceRequest() {
		List<ZoneInfo> zoneInfos = new ArrayList<>();
		zoneInfos.add(new ZoneInfo("A", 100));
		zoneInfos.add(new ZoneInfo("B", 100));
		placeRequest = new PlaceRequest("공연장", "Address", zoneInfos);
		goodsRequest = new GoodsRequest(
			"강아지 공연",
			"강아지 쇼케이스",
			LocalDate.of(2023, 3, 1),
			LocalDate.of(2023, 3, 2),
			12,
			LocalTime.of(15, 0),
			360,
			"공연"
		);
	}

	@Test
	void 공연장_생성_테스트() {
		// given
		Place place = placeRequest.toEntity(100);
		List<Zone> zoneList = new ArrayList<>();
		zoneList.add(
			Zone
				.builder()
				.place(place)
				.name(
					placeRequest.getZoneInfos().get(0).getZone())
				.seatNumber(
					placeRequest.getZoneInfos().get(0).getSeatNumber())
				.build()
		);
		zoneList.add(Zone
			.builder()
			.place(place)
			.name(
				placeRequest.getZoneInfos().get(1).getZone())
			.seatNumber(
				placeRequest.getZoneInfos().get(1).getSeatNumber())
			.build()
		);

		//when
		given(placeService.createPlace(any())).willReturn(place);
		given(zoneService.createZone(any(), any())).willReturn(zoneList);
		List<PlaceResponse> response = adminService.createPlaceAndZone(placeRequest);

		//then
		assertEquals("공연장", place.getName());
		assertEquals("Address", place.getAddress());
		assertEquals(response.get(0).getZone(), zoneList.get(0).getName());
		assertEquals(response.get(0).getZoneCountSeat(), zoneList.get(0).getSeatNumber());
		assertEquals(response.get(1).getZone(), zoneList.get(1).getName());
		assertEquals(response.get(1).getZoneCountSeat(), zoneList.get(1).getSeatNumber());

	}

	@Test
	void 공연_공연정보_공연이미지_공연카테고리_회차_생성_테스트() {
		// given
		Place place = Mockito.mock();
		GoodsInfo goodsInfo = goodsRequest.toEntity();
		Goods goods = goodsRequest.toEntity(place, goodsInfo);

		List<String> fileUrl = new ArrayList<>();
		fileUrl.add("goods/thumbnail/1/51579925-f563-4c75-9999-e2264dadbdab");
		fileUrl.add("goods/general/1/0aebcd4f-b2b5-4bbc-b8f8-a10c4b8f2c17");

		List<GoodsImage> goodsImage = new ArrayList<>();
		goodsImage.add(GoodsImage.builder().s3Key(fileUrl.get(0)).type("대표").goodsInfo(goodsInfo).build());
		goodsImage.add(GoodsImage.builder().s3Key(fileUrl.get(1)).type("일반").goodsInfo(goodsInfo).build());

		goodsInfo.addGoodsImage(goodsImage);
		GoodsCategory goodsCategory = GoodsCategory.builder().name("공연").build();
		goodsInfo.updateGoodsCategory(goodsCategory);

		List<Schedule> scheduleList = new ArrayList<>();
		scheduleList.add(
			Schedule
				.builder()
				.sequence(1)
				.startDateTime(
					LocalDateTime.of(2023, 3, 1, 15, 0, 0))
				.goods(goods)
				.build()
		);
		scheduleList.add(
			Schedule
				.builder()
				.sequence(2)
				.startDateTime(
					LocalDateTime.of(2023, 3, 2, 15, 0, 0))
				.goods(goods)
				.build()
		);
		LocalDateTime startDateTime = scheduleList.get(0).getStartDateTime();
		LocalDateTime endDateTime = scheduleList.get(1).getStartDateTime();
		long daysBetween = ChronoUnit.DAYS.between(startDateTime, endDateTime);

		// when
		given(placeService.getReferenceById(1L)).willReturn(place);
		given(goodsInfoService.createGoodsInfo(any(GoodsRequest.class))).willReturn(goodsInfo);
		given(goodsInfoService.createGoodsImage(any(), any())).willReturn(goodsImage);
		given(goodsInfoService.createGoodsCategory(any())).willReturn(goodsCategory);
		given(goodsService.createGoods(goodsRequest, place, goodsInfo)).willReturn(goods);
		adminService.createGoodsBundleAndSchedule(1L, goodsRequest, mock());

		// then
		verify(placeService, times(1)).getReferenceById(anyLong());
		verify(goodsInfoService, times(1)).createGoodsInfo(any(GoodsRequest.class));
		verify(goodsInfoService, times(1)).createGoodsImage(any(), any(GoodsInfo.class));
		verify(goodsInfoService, times(1)).createGoodsCategory(any());
		verify(goodsService, times(1))
			.createGoods(
				any(GoodsRequest.class),
				any(Place.class),
				any(GoodsInfo.class)
			);
		verify(scheduleService, times(1)).createSchedule(any(Goods.class), any(LocalTime.class));

		assertEquals(goodsImage.get(0).getS3Key(), goodsInfo.getGoodsImage().get(0).getS3Key());
		assertEquals(goodsImage.get(1).getS3Key(), goodsInfo.getGoodsImage().get(1).getS3Key());
		assertEquals(goodsImage.get(0).getType(), goodsInfo.getGoodsImage().get(0).getType());
		assertEquals(goodsImage.get(1).getType(), goodsInfo.getGoodsImage().get(1).getType());
		assertEquals(goodsCategory.getName(), goodsInfo.getGoodsCategory().getName());
		assertEquals((int)daysBetween + 1, scheduleList.get(1).getSequence());
		assertEquals(goodsRequest.getStartTime().getMinute(), scheduleList.get(1).getStartDateTime().getMinute());
		assertEquals(goodsRequest.getStartTime().getHour(), scheduleList.get(1).getStartDateTime().getHour());
		assertEquals(scheduleList.get(1).getGoods().getGoodsInfo(), goodsInfo);

	}
}
