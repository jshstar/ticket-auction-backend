package com.sparta.ticketauction.domain.place.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sparta.ticketauction.domain.place.dto.ZoneInfo;
import com.sparta.ticketauction.domain.place.entity.Zone;
import com.sparta.ticketauction.domain.place.repository.ZoneRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZoneServiceImpl implements ZoneService {

	private final ZoneRepository zoneRepository;

	// 공연장 구역 생성
	@Override
	public List<Zone> createZone(List<ZoneInfo> zoneInfos) {
		List<Zone> zoneList = new ArrayList<>();

		for (ZoneInfo zoneInfo : zoneInfos) {
			Zone zone =
				Zone
					.builder()
					.name(zoneInfo.getZone())
					.seatNumber(zoneInfo.getSeatNumber())
					.build();
			zoneList.add(zone);
		}

		return zoneRepository.saveAll(zoneList);
	}

	// 구역 프록시 객체 생성
	@Override
	public Zone getReferenceById(Long zoneId) {
		return zoneRepository.getReferenceById(zoneId);
	}

}
