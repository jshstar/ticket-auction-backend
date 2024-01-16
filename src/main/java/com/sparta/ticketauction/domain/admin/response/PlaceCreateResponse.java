package com.sparta.ticketauction.domain.admin.response;

import com.sparta.ticketauction.domain.place.entity.Place;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceCreateResponse {
	private final String zone;

	private final Integer zoneCountSeat;

	private final Long placeId;

	public PlaceCreateResponse(String zone, Integer zoneCountSeat, Place place) {
		this.zone = zone;
		this.zoneCountSeat = zoneCountSeat;
		this.placeId = place.getId();
	}
}
