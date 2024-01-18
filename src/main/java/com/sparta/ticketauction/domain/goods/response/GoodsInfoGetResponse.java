package com.sparta.ticketauction.domain.goods.response;

import static com.sparta.ticketauction.domain.admin.service.AdminServiceImpl.*;

import com.sparta.ticketauction.domain.goods.entity.GoodsImage;
import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;
import com.sparta.ticketauction.domain.goods.entity.ImageType;

import lombok.Getter;

@Getter
public class GoodsInfoGetResponse {
	private final Long goodsInfoId;

	private final String name;

	private final String s3Url;

	public GoodsInfoGetResponse(GoodsInfo goodsInfo) {
		this.goodsInfoId = goodsInfo.getId();
		this.name = goodsInfo.getName();
		this.s3Url = S3_PATH + goodsInfo.getGoodsImage().stream()
			.filter(image -> ImageType.POSTER_IMG.equals(image.getType()))
			.map(GoodsImage::getS3Key)
			.findFirst()
			.orElse(null);
	}
}
