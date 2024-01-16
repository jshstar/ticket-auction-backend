package com.sparta.ticketauction.domain.place.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.ticketauction.domain.admin.request.PlaceCreateRequest;
import com.sparta.ticketauction.domain.place.dto.ZoneInfo;
import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.place.repository.PlaceRepository;
import com.sparta.ticketauction.domain.place.response.PlaceGetResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

	private final PlaceRepository placeRepository;

	// 공연장 생성
	@Override
	public Place createPlace(PlaceCreateRequest placeCreateRequest) {
		List<ZoneInfo> zoneInfos = placeCreateRequest.getZoneInfos();

		Integer totalSeat = calculateSeats(zoneInfos);

		Place place = placeCreateRequest.toEntity(totalSeat);

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

	// 공연장 전체조회
	@Override
	@Transactional(readOnly = true)
	public List<PlaceGetResponse> getAllPlace() {
		List<Place> placeList = placeRepository.findAll();
		return placeList.stream().map(PlaceGetResponse::new).toList();
	}

	// 공연장 프록시 객체 조회
	@Override
	public Place getReferenceById(Long placeId) {
		return placeRepository.getReferenceById(placeId);
	}

}
