package com.sparta.ticketauction.domain.goods.response;

import static com.sparta.ticketauction.domain.admin.service.AdminServiceImpl.*;

import lombok.Getter;

@Getter
public class GoodsGetQueryResponse {
	private final Long goodsId;
	private final String title;
	private final String s3Url;

	public GoodsGetQueryResponse(Long goodsId, String title, String s3Key) {
		this.goodsId = goodsId;
		this.title = title;
		if (s3Key != null) {
			this.s3Url = S3_PATH + s3Key;
		} else {
			this.s3Url = null;
		}

	}
}
