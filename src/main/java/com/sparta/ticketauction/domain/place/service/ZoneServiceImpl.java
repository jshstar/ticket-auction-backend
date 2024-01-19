package com.sparta.ticketauction.domain.place.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.goods.service.GoodsService;
import com.sparta.ticketauction.domain.place.dto.ZoneInfo;
import com.sparta.ticketauction.domain.place.entity.Zone;
import com.sparta.ticketauction.domain.place.repository.ZoneRepository;
import com.sparta.ticketauction.domain.place.response.ZoneGetResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZoneServiceImpl implements ZoneService {

	private final ZoneRepository zoneRepository;

	private final GoodsService goodsService;

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

	// 공연장 구역 전체 조회
	@Override
	@Transactional(readOnly = true)
	public List<ZoneGetResponse> getAllZone(Long placeId) {
		List<Zone> zoneList = zoneRepository.findAllByPlaceId(placeId);
		return zoneList.stream().map(ZoneGetResponse::new).toList();
	}

	// 구역 프록시 객체 생성
	@Override
	public Zone getReferenceById(Long zoneId) {
		return zoneRepository.getReferenceById(zoneId);
	}

	@Override
	public List<ZoneGetResponse> getAllZoneFromGoods(Long goodsId) {
		Goods goods = goodsService.findByGoodsId(goodsId);
		return goods.getPlace().getZones().stream().map(ZoneGetResponse::new).toList();
	}

}
