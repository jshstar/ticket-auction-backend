package com.sparta.ticketauction.domain.goods.response;

import java.util.List;

import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;

import lombok.Getter;

@Getter
public class GoodsInfoGetResponse {
	private final Long goodsId;

	private final String name;

	private final String description;

	private final Integer runningTime;

	private final String ageGrade;

	private final String goodsCategory;

	private final List<GoodsImageResponse> goodsImages;

	public GoodsInfoGetResponse(GoodsInfo goodsInfo) {
		// TODO: 2024-01-16 이후에 공연정보에 따른 공연 추가 페이지 구성시 리스트에서 원하는 공연을 가져오는 로직 수정
		this.goodsId = goodsInfo.getGoods().get(0).getId();
		this.name = goodsInfo.getName();
		this.description = goodsInfo.getDescription();
		this.runningTime = goodsInfo.getRunningTime();
		this.ageGrade = goodsInfo.getAgeGrade().getKorea();
		this.goodsCategory = goodsInfo.getGoodsCategory().getName();
		this.goodsImages = goodsInfo.getGoodsImage().stream().map(GoodsImageResponse::new).toList();
	}
}
