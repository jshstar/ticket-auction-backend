package com.sparta.ticketauction.domain.goods.response;

import static com.sparta.ticketauction.domain.admin.service.AdminServiceImpl.*;

import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;

import lombok.Getter;

@Getter
public class GoodsInfoResponse {
	private final Long goodsInfoId;

	private final String name;

	private final String s3Url;

	public GoodsInfoResponse(GoodsInfo goodsInfo) {
		this.goodsInfoId = goodsInfo.getId();
		this.name = goodsInfo.getName();
		this.s3Url = S3_PATH + goodsInfo.getGoodsImage().get(0).getS3Key();
	}
}
