package com.sparta.ticketauction.domain.goods.response;

import lombok.Getter;

@Getter
public class GoodsResponse {
	private final Long goodsId;

	private final String title;

	private final String s3Url;

	public GoodsResponse(GoodsGetQueryResponse goodsGetCursorResponse) {
		this.goodsId = goodsGetCursorResponse.getGoodsId();
		this.title = goodsGetCursorResponse.getTitle();
		this.s3Url = goodsGetCursorResponse.getS3Url();
	}
}
