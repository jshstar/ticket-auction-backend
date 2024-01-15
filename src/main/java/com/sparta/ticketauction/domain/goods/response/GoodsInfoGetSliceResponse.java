package com.sparta.ticketauction.domain.goods.response;

import org.springframework.data.domain.Slice;

import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;

import lombok.Getter;

@Getter
public class GoodsInfoGetSliceResponse {
	private final Slice<GoodsInfoResponse> goodsInfoSlice;

	public GoodsInfoGetSliceResponse(Slice<GoodsInfo> goodsInfoSlice) {
		this.goodsInfoSlice = goodsInfoSlice.map(GoodsInfoResponse::new);
	}
}
