package com.sparta.ticketauction.domain.place.service;

import org.springframework.stereotype.Service;

import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.place.repository.PlaceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

	private final PlaceRepository placeRepository;

	public Place savePlace(Place place) {
		return placeRepository.save(place);
	}
}
