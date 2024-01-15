package com.sparta.ticketauction.domain.admin.controller;

import static com.sparta.ticketauction.global.response.SuccessCode.*;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sparta.ticketauction.domain.admin.request.GoodsRequest;
import com.sparta.ticketauction.domain.admin.request.GradeRequest;
import com.sparta.ticketauction.domain.admin.request.PlaceRequest;
import com.sparta.ticketauction.domain.admin.request.ZoneGradeRequest;
import com.sparta.ticketauction.domain.admin.response.GoodsResponse;
import com.sparta.ticketauction.domain.admin.response.GradeResponse;
import com.sparta.ticketauction.domain.admin.response.PlaceResponse;
import com.sparta.ticketauction.domain.admin.response.ZoneGradeResponse;
import com.sparta.ticketauction.domain.admin.service.AdminServiceImpl;
import com.sparta.ticketauction.domain.auction.request.AuctionCreateRequest;
import com.sparta.ticketauction.global.dto.EmptyObject;
import com.sparta.ticketauction.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

	private final AdminServiceImpl adminService;

	// 공연장 및 구역 생성
	@PostMapping("/admin/places")
	public ResponseEntity<ApiResponse<List<PlaceResponse>>> createPlaceAndZone(
		@Valid @RequestBody PlaceRequest placeRequest
	) {
		List<PlaceResponse> placeResponseList = adminService.createPlaceAndZone(placeRequest);
		return ResponseEntity
			.status(SUCCESS_PLACE_AND_ZONE_CREATE.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_PLACE_AND_ZONE_CREATE.getCode(),
					SUCCESS_PLACE_AND_ZONE_CREATE.getMessage(),
					placeResponseList)
			);
	}

	//  공연과 관련된 공연 정보, 공연 카테고리, 공연 이미지, 공연 및 회차 생성
	@PostMapping(value = "/admin/places/{placeId}/goods",
		consumes = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE
		})
	public ResponseEntity<ApiResponse<GoodsResponse>> createGoodsBundleAndSchedule(
		@Valid @RequestPart GoodsRequest goodsRequest,
		@RequestPart(value = "files", required = false) List<MultipartFile> multipartFiles,
		@PathVariable Long placeId
	) {
		GoodsResponse goodsResponse =
			adminService.createGoodsBundleAndSchedule(
				placeId,
				goodsRequest,
				multipartFiles
			);
		return ResponseEntity
			.status(SUCCESS_GOODS_AND_SCHEDULE_CREATE.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_GOODS_AND_SCHEDULE_CREATE.getCode(),
					SUCCESS_GOODS_AND_SCHEDULE_CREATE.getMessage(),
					goodsResponse
				)
			);
	}

	// 등급 생성
	@PostMapping("/admin/goods/{goodsId}/grades")
	public ResponseEntity<ApiResponse<GradeResponse>> createGrade(
		@PathVariable Long goodsId,
		@Valid @RequestBody GradeRequest gradeRequest) {

		GradeResponse gradeResponse = adminService.createGrade(goodsId, gradeRequest);

		return ResponseEntity
			.status(SUCCESS_GRADE_CREATE.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_GRADE_CREATE.getCode(),
					SUCCESS_GRADE_CREATE.getMessage(),
					gradeResponse)
			);
	}

	// 구역 등급 생성
	@PostMapping("/admin/zone-grades")
	public ResponseEntity<ApiResponse<ZoneGradeResponse>> createZoneGrade(
		@RequestBody ZoneGradeRequest zoneGradeRequest
	) {
		ZoneGradeResponse zoneGradeResponse = adminService.createZoneGrade(zoneGradeRequest);
		return ResponseEntity
			.status(SUCCESS_ZONE_GRADE_CREATE.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_ZONE_GRADE_CREATE.getCode(),
					SUCCESS_ZONE_GRADE_CREATE.getMessage(),
					zoneGradeResponse
				)
			);
	}

	// 경매 생성
	@PostMapping("/admin/schedules/{scheduleId}/auctions")
	public ResponseEntity<ApiResponse<EmptyObject>> createAuction(
		@PathVariable Long scheduleId,
		@RequestParam Long zoneGradeId,
		@RequestBody AuctionCreateRequest auctionCreateRequest
	) {
		adminService.createAuction(scheduleId, zoneGradeId, auctionCreateRequest);
		return ResponseEntity
			.status(SUCCESS_AUCTION_CREATE.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_AUCTION_CREATE.getCode(),
					SUCCESS_AUCTION_CREATE.getMessage()
				)
			);
	}

}
