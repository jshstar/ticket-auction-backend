package com.sparta.ticketauction.domain.admin.zone.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.place.entity.Zone;
import com.sparta.ticketauction.domain.place.repository.ZoneRepository;
import com.sparta.ticketauction.domain.place.response.ZoneGetResponse;
import com.sparta.ticketauction.domain.place.service.ZoneServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ZoneServiceTest {

	@InjectMocks
	ZoneServiceImpl zoneService;

	@Mock
	ZoneRepository zoneRepository;

	@Test
	void 공연_구역_전체조회_테스트() {
		// given
		List<Place> placeList = new ArrayList<>(List.of(Place
			.builder()
			.name("공연장1")
			.address("주소1")
			.countSeats(300)
			.build()
		)
		);
		ReflectionTestUtils.setField(placeList.get(0), "id", 1L);

		List<Zone> zoneList = new ArrayList<>(
			List.of(
				Zone
					.builder()
					.name("A")
					.seatNumber(100)
					.build()
			)
		);
		ReflectionTestUtils.setField(zoneList.get(0), "id", 1L);

		// when
		given(zoneRepository.findAllByPlaceId(anyLong())).willReturn(zoneList);
		List<ZoneGetResponse> zoneGetResponse = zoneService.getAllZone(1L);

		// then
		assertEquals(zoneGetResponse.get(0).getZoneId(), zoneList.get(0).getId());
		assertEquals(zoneGetResponse.get(0).getName(), zoneList.get(0).getName());
		assertEquals(zoneGetResponse.get(0).getSeatNumber(), zoneList.get(0).getSeatNumber());
	}

}
