package com.sparta.ticketauction.domain.goods.response;

import java.util.List;
import java.util.stream.Collectors;

import com.sparta.ticketauction.domain.goods.entity.Goods;

import lombok.Getter;

@Getter
public class GoodsGetCursorResponse {
	private final List<GoodsResponse> goodsResponses;
	private final Long nextCursorId;
	private final boolean hasNext;

	public GoodsGetCursorResponse(List<Goods> goodsList, Long nextCursorId, boolean hasNext) {
		this.goodsResponses = goodsList.stream()
			.map(GoodsResponse::new)
			.collect(Collectors.toList());
		this.nextCursorId = nextCursorId;
		this.hasNext = hasNext;
	}
}
