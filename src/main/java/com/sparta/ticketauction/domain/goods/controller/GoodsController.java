package com.sparta.ticketauction.domain.goods.controller;

import static com.sparta.ticketauction.global.response.SuccessCode.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.ticketauction.domain.goods.response.GoodsAuctionSeatInfoResponse;
import com.sparta.ticketauction.domain.goods.response.GoodsCategoryGetResponse;
import com.sparta.ticketauction.domain.goods.response.GoodsGetCursorResponse;
import com.sparta.ticketauction.domain.goods.response.GoodsGetResponse;
import com.sparta.ticketauction.domain.goods.response.GoodsInfoGetResponse;
import com.sparta.ticketauction.domain.goods.response.GoodsSeatInfoResponse;
import com.sparta.ticketauction.domain.goods.service.GoodsService;
import com.sparta.ticketauction.domain.reservation.reservation_seat.repository.ReservationSeatRepository;
import com.sparta.ticketauction.domain.seat.response.ReservedSeatResponse;
import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.global.annotaion.CurrentUser;
import com.sparta.ticketauction.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GoodsController {

	private final GoodsService goodsService;

	private final ReservationSeatRepository reservationSeatRepository;

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
	public ResponseEntity<ApiResponse<GoodsGetCursorResponse>> getAllGoods(
		@RequestParam(value = "cursorId", required = false) Long cursorId,
		@RequestParam(value = "size", defaultValue = "10") int size,
		@RequestParam(value = "categoryName", required = false) String categoryName
	) {
		GoodsGetCursorResponse goodsGetCursorResponse = goodsService.getGoodsWithCursor(cursorId, size, categoryName);
		return ResponseEntity
			.status(SUCCESS_GET_SLICE_GOODS.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_GET_SLICE_GOODS.getCode(),
					SUCCESS_GET_SLICE_GOODS.getMessage(),
					goodsGetCursorResponse)
			);
	}

	// 공연 카테고리 조회
	@GetMapping("/goods-categorys")
	public ResponseEntity<ApiResponse<List<GoodsCategoryGetResponse>>> getAllCategory() {
		List<GoodsCategoryGetResponse> goodsCategoryGetResponseList = goodsService.getAllGoodsCategory();

		return ResponseEntity
			.status(
				SUCCESS_GET_ALL_GOODS_CATEGORY.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_GET_ALL_GOODS_CATEGORY.getCode(),
					SUCCESS_GET_ALL_GOODS_CATEGORY.getMessage(),
					goodsCategoryGetResponseList));

	}

	@GetMapping("/goods/{goodsId}/seats")
	public ResponseEntity<ApiResponse<GoodsSeatInfoResponse>> getGoodsSeatInfo(@PathVariable Long goodsId) {
		GoodsSeatInfoResponse response = goodsService.findGoodsSeatInfo(goodsId);
		return ResponseEntity
			.status(SUCCESS_GET_GOODS_SEAT_INFO.getHttpStatus())
			.body(ApiResponse.of(
				SUCCESS_GET_GOODS_SEAT_INFO.getCode(),
				SUCCESS_GET_GOODS_SEAT_INFO.getMessage(),
				response
			));
	}

	@GetMapping("/goods/{goodsId}/auction-seats")
	public ResponseEntity<ApiResponse<GoodsAuctionSeatInfoResponse>> getGoodsAuctionSeatInfo(
		@PathVariable Long goodsId,
		@RequestParam Long scheduleId
	) {
		GoodsAuctionSeatInfoResponse response = goodsService.findGoodsAuctionSeatInfo(scheduleId, goodsId);
		return ResponseEntity
			.status(SUCCESS_GET_GOODS_AUCTION_INFO.getHttpStatus())
			.body(ApiResponse.of(
				SUCCESS_GET_GOODS_AUCTION_INFO.getCode(),
				SUCCESS_GET_GOODS_AUCTION_INFO.getMessage(),
				response
			));
	}

	@GetMapping("/goods/{scheduleId}/reserved-seats")
	public ResponseEntity<ApiResponse<List<ReservedSeatResponse>>> getReservedSeats(@PathVariable Long scheduleId) {
		// List<ReservedSeatResponse> response = reservationSeatRepository.findReservedSeats(scheduleId);
		List<ReservedSeatResponse> response = reservationSeatRepository.findReservedSeatsFromCache(scheduleId);

		return ResponseEntity
			.status(SUCCESS_GET_GOODS_RESERVED_SEAT_INFO.getHttpStatus())
			.body(ApiResponse.of(
				SUCCESS_GET_GOODS_RESERVED_SEAT_INFO.getCode(),
				SUCCESS_GET_GOODS_RESERVED_SEAT_INFO.getMessage(),
				response
			));
	}
}
