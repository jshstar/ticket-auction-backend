package com.sparta.ticketauction.domain.goods.controller;

import static com.sparta.ticketauction.global.response.SuccessCode.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.ticketauction.domain.goods.response.GoodsGetResponse;
import com.sparta.ticketauction.domain.goods.response.GoodsGetSliceResponse;
import com.sparta.ticketauction.domain.goods.response.GoodsInfoGetResponse;
import com.sparta.ticketauction.domain.goods.service.GoodsService;
import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.global.annotaion.CurrentUser;
import com.sparta.ticketauction.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GoodsController {

	private final GoodsService goodsService;

	// 공연 정보 전체 조회
	@GetMapping("goods-infos")
	public ResponseEntity<ApiResponse<List<GoodsInfoGetResponse>>> getAllGoodsInfo(@CurrentUser User user) {
		List<GoodsInfoGetResponse> goodsInfoGetResponseList = goodsService.getAllGoodsInfo();
		return ResponseEntity
			.status(
				SUCCESS_GET_ALL_GOODS_INFO.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_GET_ALL_GOODS_INFO.getCode(),
					SUCCESS_GET_ALL_GOODS_INFO.getMessage(),
					goodsInfoGetResponseList));
	}

	// 공연 단건 조회
	@GetMapping("/goods/{goodsId}")
	public ResponseEntity<ApiResponse<GoodsGetResponse>> getGoods(@PathVariable Long goodsId) {
		GoodsGetResponse goodsGetResponse = goodsService.getGoods(goodsId);
		return ResponseEntity
			.status(
				SUCCESS_GET_GOODS.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_GET_GOODS.getCode(),
					SUCCESS_GET_GOODS.getMessage(),
					goodsGetResponse)
			);
	}

	// 공연 페이징 전체 조회
	@GetMapping("/goods")
	public ResponseEntity<ApiResponse<GoodsGetSliceResponse>> getAllGoods(
		Pageable pageable,
		@RequestParam(value = "categoryName", required = false) String categoryName) {
		GoodsGetSliceResponse goodsGetSliceResponse = goodsService.getSliceGoods(pageable, categoryName);
		return ResponseEntity
			.status(SUCCESS_GET_SLICE_GOODS.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_GET_SLICE_GOODS.getCode(),
					SUCCESS_GET_SLICE_GOODS.getMessage(),
					goodsGetSliceResponse)
			);
	}
}
