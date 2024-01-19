package com.sparta.ticketauction.domain.place.controller;

import static com.sparta.ticketauction.global.response.SuccessCode.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.ticketauction.domain.place.response.ZoneGetResponse;
import com.sparta.ticketauction.domain.place.service.ZoneService;
import com.sparta.ticketauction.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ZoneController {

	private final ZoneService zoneService;

	// 공연장 구역 전체 조회
	@GetMapping("/places/{placeId}/zones")
	public ResponseEntity<ApiResponse<List<ZoneGetResponse>>> getAllZone(@PathVariable Long placeId) {
		List<ZoneGetResponse> zoneGetResponses = zoneService.getAllZone(placeId);
		return ResponseEntity
			.status(SUCCESS_GET_ALL_ZONE.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_GET_ALL_ZONE.getCode(),
					SUCCESS_GET_ALL_ZONE.getMessage(),
					zoneGetResponses)
			);
	}

	// 해당 공연의 공연장 구역 전체 조회
	@GetMapping("/zones")
	public ResponseEntity<ApiResponse<List<ZoneGetResponse>>> getAllZoneFromGoods(@RequestParam Long goodsId) {
		List<ZoneGetResponse> zoneGetResponses = zoneService.getAllZoneFromGoods(goodsId);
		return ResponseEntity
			.status(
				SUCCESS_GET_ALL_ZONE_FROM_GOODS.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_GET_ALL_ZONE_FROM_GOODS.getCode(),
					SUCCESS_GET_ALL_ZONE_FROM_GOODS.getMessage(), zoneGetResponses)
			);
	}

}
