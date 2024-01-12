package com.sparta.ticketauction.domain.place.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sparta.ticketauction.domain.admin.request.PlaceRequest;
import com.sparta.ticketauction.domain.place.dto.ZoneInfo;
import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.place.entity.Zone;
import com.sparta.ticketauction.domain.place.repository.PlaceRepository;
import com.sparta.ticketauction.domain.place.repository.ZoneRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

	private final PlaceRepository placeRepository;

	private final ZoneRepository zoneRepository;

	// 공연장 생성
	@Override
	public Place createPlace(PlaceRequest placeRequest) {
		List<ZoneInfo> zoneInfos = placeRequest.getZoneInfos();

		Integer totalSeat = calculateSeats(zoneInfos);

		Place place = placeRequest.toEntity(totalSeat);

		return placeRepository.save(place);
	}

	// 공연장 총 좌석 개수 계산
	@Override
	public Integer calculateSeats(List<ZoneInfo> seats) {
		Integer totalSeat = 0;

		for (ZoneInfo seat : seats) {
			totalSeat += seat.getSeatNumber();
		}

		return totalSeat;
	}

	// 공연장 구역 생성
	public List<Zone> createZone(Place place, List<ZoneInfo> zoneInfos) {
		List<Zone> zoneList = new ArrayList<>();

		for (ZoneInfo zoneInfo : zoneInfos) {
			Zone zone =
				Zone
					.builder()
					.place(place)
					.name(zoneInfo.getZone())
					.seatNumber(zoneInfo.getSeatNumber())
					.build();
			zoneList.add(zone);
		}

		return zoneRepository.saveAll(zoneList);
	}
}
