package com.sparta.ticketauction.domain.goods.response;

import static com.sparta.ticketauction.domain.admin.service.AdminServiceImpl.*;

import com.sparta.ticketauction.domain.goods.entity.GoodsImage;

import lombok.Getter;

@Getter
public class GoodsImageResponse {
	private final String imageType;

	private final String s3Url;

	public GoodsImageResponse(GoodsImage goodsImage) {
		this.imageType = goodsImage.getType().getType();
		this.s3Url = S3_PATH + goodsImage.getS3Key();
	}
}
