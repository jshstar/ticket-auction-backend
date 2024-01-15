package com.sparta.ticketauction.domain.goods.controller;

import static com.sparta.ticketauction.global.response.SuccessCode.*;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.ticketauction.domain.goods.response.GoodsInfoGetResponse;
import com.sparta.ticketauction.domain.goods.response.GoodsInfoGetSliceResponse;
import com.sparta.ticketauction.domain.goods.service.GoodsInfoService;
import com.sparta.ticketauction.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GoodsController {

	private final GoodsInfoService goodsInfoService;

	// 공연 정보 단건 조회
	@GetMapping("/goods-info/{goodsInfoId}")
	public ResponseEntity<ApiResponse<GoodsInfoGetResponse>> getGoodsInfo(@PathVariable Long goodsInfoId) {
		GoodsInfoGetResponse goodsInfoGetResponse = goodsInfoService.getGoodsInfo(goodsInfoId);
		return ResponseEntity
			.status(SUCCESS_GET_GOODS_INFO.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_GET_GOODS_INFO.getCode(),
					SUCCESS_GET_GOODS_INFO.getMessage(),
					goodsInfoGetResponse)
			);
	}

	@GetMapping("/goods-info/slice")
	public ResponseEntity<ApiResponse<GoodsInfoGetSliceResponse>> getSliceGoodsInfo(Pageable pageable,
		@RequestParam(value = "categoryName", required = false) String categoryName) {
		GoodsInfoGetSliceResponse goodsInfoGetSliceResponse = goodsInfoService.getSliceGoodsInfo(pageable,
			categoryName);
		return ResponseEntity
			.status(
				SUCCESS_GET_SLICE_GOODS_INFO.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_GET_SLICE_GOODS_INFO.getCode(),
					SUCCESS_GET_SLICE_GOODS_INFO.getMessage(),
					goodsInfoGetSliceResponse));
	}
}
