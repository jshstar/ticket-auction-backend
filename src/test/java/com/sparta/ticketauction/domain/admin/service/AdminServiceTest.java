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

import com.sparta.ticketauction.domain.admin.request.GoodsRequest;
import com.sparta.ticketauction.domain.admin.request.PlaceRequest;
import com.sparta.ticketauction.domain.admin.response.PlaceResponse;
import com.sparta.ticketauction.domain.place.dto.ZoneInfo;
import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.place.entity.Zone;
import com.sparta.ticketauction.domain.place.service.PlaceServiceImpl;
import com.sparta.ticketauction.domain.place.service.ZoneServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

	@InjectMocks
	AdminServiceImpl adminService;

	@Mock
	PlaceServiceImpl placeService;

	@Mock
	ZoneServiceImpl zoneService;

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
}
