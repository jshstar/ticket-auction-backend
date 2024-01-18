package com.sparta.ticketauction.domain.goods.response;

import org.springframework.data.domain.Slice;

import com.sparta.ticketauction.domain.goods.entity.Goods;

import lombok.Getter;

@Getter
public class GoodsGetSliceResponse {
	private final Slice<GoodsResponse> goodsSlice;

	public GoodsGetSliceResponse(Slice<Goods> goodsSlice) {
		this.goodsSlice = goodsSlice.map(GoodsResponse::new);
	}
}
