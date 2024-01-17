package com.sparta.ticketauction.domain.admin.response;

import lombok.Getter;

@Getter
public class GoodsInfoCreateResponse {
	private final Long goodsInfoId;

	public GoodsInfoCreateResponse(Long goodsInfoId) {
		this.goodsInfoId = goodsInfoId;
	}
}
