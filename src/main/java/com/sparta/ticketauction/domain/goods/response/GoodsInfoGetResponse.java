package com.sparta.ticketauction.domain.goods.response;

import java.util.List;

import com.sparta.ticketauction.domain.goods.entity.AgeGrade;
import com.sparta.ticketauction.domain.goods.entity.GoodsCategory;
import com.sparta.ticketauction.domain.goods.entity.GoodsImage;
import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;

import lombok.Getter;

@Getter
public class GoodsInfoGetResponse {
	private final String name;

	private final String description;

	private final Integer runningTime;

	private final AgeGrade ageGrade;

	private final GoodsCategory goodsCategory;

	private final List<GoodsImage> goodsImages;

	public GoodsInfoGetResponse(GoodsInfo goodsInfo) {
		this.name = goodsInfo.getName();
		this.description = goodsInfo.getDescription();
		this.runningTime = goodsInfo.getRunningTime();
		this.ageGrade = goodsInfo.getAgeGrade();
		this.goodsCategory = goodsInfo.getGoodsCategory();
		this.goodsImages = goodsInfo.getGoodsImage();
	}
}
