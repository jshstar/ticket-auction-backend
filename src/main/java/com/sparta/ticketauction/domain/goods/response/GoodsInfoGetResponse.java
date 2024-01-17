package com.sparta.ticketauction.domain.goods.response;

import java.util.List;

import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;

import lombok.Getter;

@Getter
public class GoodsInfoGetResponse {
	private final List<GoodsGetResponse> goodsGetResponse;

	private final String name;

	private final String description;

	private final Integer runningTime;

	private final String ageGrade;

	private final String goodsCategory;

	private final List<GoodsImageResponse> goodsImages;

	public GoodsInfoGetResponse(GoodsInfo goodsInfo, List<Goods> goodsList) {
		this.goodsGetResponse = goodsList.stream().map(GoodsGetResponse::new).toList();
		this.name = goodsInfo.getName();
		this.description = goodsInfo.getDescription();
		this.runningTime = goodsInfo.getRunningTime();
		this.ageGrade = goodsInfo.getAgeGrade().getKorea();
		this.goodsCategory = goodsInfo.getGoodsCategory().getName();
		this.goodsImages = goodsInfo.getGoodsImage().stream().map(GoodsImageResponse::new).toList();
	}
}
