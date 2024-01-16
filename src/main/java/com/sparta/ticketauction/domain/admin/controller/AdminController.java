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

import com.sparta.ticketauction.domain.admin.request.GoodsCreateRequest;
import com.sparta.ticketauction.domain.admin.request.GoodsInfoCreateRequest;
import com.sparta.ticketauction.domain.admin.request.GradeCreateRequest;
import com.sparta.ticketauction.domain.admin.request.PlaceCreateRequest;
import com.sparta.ticketauction.domain.admin.request.ZoneGradeCreateRequest;
import com.sparta.ticketauction.domain.admin.response.GoodsCreateResponse;
import com.sparta.ticketauction.domain.admin.response.GoodsInfoCreateResponse;
import com.sparta.ticketauction.domain.admin.response.GradeCreateResponse;
import com.sparta.ticketauction.domain.admin.response.PlaceCreateResponse;
import com.sparta.ticketauction.domain.admin.response.ZoneGradeCreateResponse;
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
	public ResponseEntity<ApiResponse<List<PlaceCreateResponse>>> createPlaceAndZone(
		@Valid @RequestBody PlaceCreateRequest placeCreateRequest
	) {
		List<PlaceCreateResponse> placeCreateResponseList = adminService.createPlaceAndZone(placeCreateRequest);
		return ResponseEntity
			.status(SUCCESS_PLACE_AND_ZONE_CREATE.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_PLACE_AND_ZONE_CREATE.getCode(),
					SUCCESS_PLACE_AND_ZONE_CREATE.getMessage(),
					placeCreateResponseList)
			);
	}

	//  공연과 관련된 공연 정보, 공연 카테고리, 공연 이미지 생성
	@PostMapping(value = "/admin/goodsInfos",
		consumes = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE
		})
	public ResponseEntity<ApiResponse<GoodsInfoCreateResponse>> createGoodsInfo(
		@Valid @RequestPart GoodsInfoCreateRequest goodsInfoCreateRequest,
		@RequestPart(value = "files", required = false) List<MultipartFile> multipartFiles,
		@PathVariable Long placeId
	) {
		GoodsInfoCreateResponse goodsInfoCreateResponse =
			adminService.createGoodsBundle(
				placeId,
				goodsInfoCreateRequest,
				multipartFiles
			);
		return ResponseEntity
			.status(SUCCESS_GOODS_INFO_CREATE.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_GOODS_INFO_CREATE.getCode(),
					SUCCESS_GOODS_INFO_CREATE.getMessage(),
					goodsInfoCreateResponse
				)
			);
	}

	// 공연 및 회차 생성
	@PostMapping("/admin/goods-infos/{goodsInfoId}/goods")
	public ResponseEntity<ApiResponse<GoodsCreateResponse>> createGoodsAndSchedule(
		@Valid @RequestBody GoodsCreateRequest goodsCreateRequest,
		@PathVariable Long goodsInfoId,
		@RequestParam Long placeId
	) {
		GoodsCreateResponse goodsCreateResponse =
			adminService.createGoodsAndSchedule(
				goodsCreateRequest,
				goodsInfoId,
				placeId
			);
		return ResponseEntity
			.status(SUCCESS_GOODS_AND_SCHEDULE_CREATE.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_GOODS_AND_SCHEDULE_CREATE.getCode(),
					SUCCESS_GOODS_AND_SCHEDULE_CREATE.getMessage(),
					goodsCreateResponse
				)
			);
	}

	// 등급 생성
	@PostMapping("/admin/goods/{goodsId}/grades")
	public ResponseEntity<ApiResponse<GradeCreateResponse>> createGrade(
		@PathVariable Long goodsId,
		@Valid @RequestBody GradeCreateRequest gradeCreateRequest) {

		GradeCreateResponse gradeCreateResponse = adminService.createGrade(goodsId, gradeCreateRequest);

		return ResponseEntity
			.status(SUCCESS_GRADE_CREATE.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_GRADE_CREATE.getCode(),
					SUCCESS_GRADE_CREATE.getMessage(),
					gradeCreateResponse)
			);
	}

	// 구역 등급 생성
	@PostMapping("/admin/zone-grades")
	public ResponseEntity<ApiResponse<ZoneGradeCreateResponse>> createZoneGrade(
		@RequestBody ZoneGradeCreateRequest zoneGradeCreateRequest
	) {
		ZoneGradeCreateResponse zoneGradeCreateResponse = adminService.createZoneGrade(zoneGradeCreateRequest);
		return ResponseEntity
			.status(SUCCESS_ZONE_GRADE_CREATE.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_ZONE_GRADE_CREATE.getCode(),
					SUCCESS_ZONE_GRADE_CREATE.getMessage(),
					zoneGradeCreateResponse
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
