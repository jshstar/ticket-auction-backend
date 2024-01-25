package com.sparta.ticketauction.domain.goods.response;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class GoodsGetCursorResponse {
	private final List<GoodsResponse> goodsResponses;
	private final Long nextCursorId;

	public GoodsGetCursorResponse(
		List<GoodsGetQueryResponse> goodsGetQueryResponses,
		Long nextCursorId
	) {
		this.goodsResponses = goodsGetQueryResponses.stream()
			.map(GoodsResponse::new)
			.collect(Collectors.toList());
		this.nextCursorId = nextCursorId;
	}

}
