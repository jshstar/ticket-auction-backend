package com.sparta.ticketauction.domain.place.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sparta.ticketauction.domain.place.dto.ZoneInfo;
import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.place.entity.Zone;
import com.sparta.ticketauction.domain.place.repository.ZoneRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZoneServiceImpl implements ZoneService {

	private final ZoneRepository zoneRepository;

	// 공연장 구역 생성
	@Override
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
