package com.sparta.ticketauction.domain.admin.response;

import lombok.Getter;

@Getter
public class GoodsCreateResponse {
	private final Long goodsId;

	public GoodsCreateResponse(Long goodsId) {
		this.goodsId = goodsId;
	}
}
