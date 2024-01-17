package com.sparta.ticketauction.domain.goods.response;

import static com.sparta.ticketauction.domain.admin.service.AdminServiceImpl.*;

import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.goods.entity.GoodsImage;
import com.sparta.ticketauction.domain.goods.entity.ImageType;

import lombok.Getter;

@Getter
public class GoodsResponse {
	private final Long goodsId;

	private final String title;

	private final String s3Url;

	public GoodsResponse(Goods goods) {
		this.goodsId = goods.getId();
		this.title = goods.getTitle();
		this.s3Url = S3_PATH + goods.getGoodsInfo().getGoodsImage().stream()
			.filter(image -> ImageType.POSTER_IMG.equals(image.getType()))
			.map(GoodsImage::getS3Key)
			.findFirst()
			.orElse(null);
	}
}
