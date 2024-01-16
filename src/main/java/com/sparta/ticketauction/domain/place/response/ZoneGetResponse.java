package com.sparta.ticketauction.domain.place.response;

import com.sparta.ticketauction.domain.place.entity.Zone;

import lombok.Getter;

@Getter
public class ZoneGetResponse {
	private final Long zoneId;

	private final String name;

	private final Integer seatNumber;

	public ZoneGetResponse(Zone zone) {
		this.zoneId = zone.getId();
		this.name = zone.getName();
		this.seatNumber = zone.getSeatNumber();
	}

}
