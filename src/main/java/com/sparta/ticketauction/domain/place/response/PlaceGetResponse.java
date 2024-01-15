package com.sparta.ticketauction.domain.place.response;

import com.sparta.ticketauction.domain.place.entity.Place;

import lombok.Getter;

@Getter
public class PlaceGetResponse {
	private final Long placeId;

	private final String name;

	public PlaceGetResponse(Place place) {
		this.placeId = place.getId();
		this.name = place.getName();
	}
}
