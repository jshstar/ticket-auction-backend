package com.sparta.ticketauction.domain.admin.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sparta.ticketauction.domain.admin.request.PlaceRequest;
import com.sparta.ticketauction.domain.admin.response.PlaceResponse;
import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.place.service.PlaceServiceImpl;
import com.sparta.ticketauction.domain.seat.request.SeatRequest;
import com.sparta.ticketauction.domain.seat.service.SeatServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

	@InjectMocks
	AdminServiceImpl adminService;

	@Mock
	PlaceServiceImpl placeService;

	@Mock
	SeatServiceImpl seatService;

	public static PlaceRequest placeRequest;

	@BeforeEach
	public void initPlaceRequest() {
		List<SeatRequest> seatRequests = new ArrayList<>();
		seatRequests.add(new SeatRequest("A", 100));
		placeRequest = new PlaceRequest("공연장", "Address", seatRequests);
	}

	@Test
	void 공연장_생성_테스트() {
		// given
		Place place = placeRequest.toEntity(100);

		//when
		given(placeService.savePlace(any(Place.class))).willReturn(place);
		List<PlaceResponse> response = adminService.createPlace(placeRequest);

		//then
		assertEquals("A", response.get(0).getZone());
		assertEquals(100, response.get(0).getZoneCountSeat());

	}
}
