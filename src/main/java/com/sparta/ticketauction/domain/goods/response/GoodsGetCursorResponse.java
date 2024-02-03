package com.sparta.ticketauction.domain.goods.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class GoodsGetCursorResponse {
	private final List<GoodsGetQueryResponse> goodsResponses;
	private final Long nextCursorId;

	@JsonCreator
	public GoodsGetCursorResponse(
		@JsonProperty("goodsGetQueryResponses") List<GoodsGetQueryResponse> goodsGetQueryResponses,
		@JsonProperty("nextCursorId") Long nextCursorId
	) {
		this.goodsResponses = goodsGetQueryResponses;
		this.nextCursorId = nextCursorId;
	}

}
