package com.sparta.ticketauction.domain.admin.response;

import lombok.Getter;

@Getter
public class GoodsResponse {
	private final Long goodsId;

	public GoodsResponse(Long goodsId) {
		this.goodsId = goodsId;
	}
}
