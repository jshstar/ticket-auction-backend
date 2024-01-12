package com.sparta.ticketauction.domain.place.service;

import static com.sparta.ticketauction.global.exception.ErrorCode.*;

import org.springframework.stereotype.Service;

import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.place.repository.PlaceRepository;
import com.sparta.ticketauction.global.exception.ApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

	private final PlaceRepository placeRepository;

	// 공연장 저장
	@Override
	public Place savePlace(Place place) {
		return placeRepository.save(place);
	}

	// 공연장 찾기
	@Override
	public Place findPlace(Long placeId) {
		return placeRepository.findById(placeId)
			.orElseThrow(() -> new ApiException(NOT_FOUND_PLACE));
	}
}
