package com.sparta.ticketauction.domain.admin.controller;

import static com.sparta.ticketauction.global.response.SuccessCode.*;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sparta.ticketauction.domain.admin.request.GoodsRequest;
import com.sparta.ticketauction.domain.admin.request.PlaceRequest;
import com.sparta.ticketauction.domain.admin.response.PlaceResponse;
import com.sparta.ticketauction.domain.admin.service.AdminServiceImpl;
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

	// 공연장 추가
	@PostMapping("/admin/places")
	public ResponseEntity<ApiResponse<List<PlaceResponse>>> createPlace(
		@Valid @RequestBody PlaceRequest placeRequest) {
		List<PlaceResponse> placeResponse = adminService.createPlace(placeRequest);
		return ResponseEntity
			.status(SUCCESS_PLACE_CREATE.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_PLACE_CREATE.getCode(),
					SUCCESS_PLACE_CREATE.getMessage(),
					placeResponse
				)
			);
	}

	@PostMapping(value = "/admin/places/{placeId}/goods",
		consumes = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE
		})
	public ResponseEntity<ApiResponse<EmptyObject>> createGoodsAndSequence(
		@Valid @RequestPart GoodsRequest goodsRequest,
		@RequestPart(value = "files", required = false) List<MultipartFile> multipartFileList,
		@PathVariable Long placeId
	) {
		adminService.createGoodsAndSequence(goodsRequest, placeId, multipartFileList);
		return ResponseEntity
			.status(SUCCESS_GOODS_AND_SEQUENCE_CREATE.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_GOODS_AND_SEQUENCE_CREATE.getCode(),
					SUCCESS_GOODS_AND_SEQUENCE_CREATE.getMessage()
				)
			);
	}

}
