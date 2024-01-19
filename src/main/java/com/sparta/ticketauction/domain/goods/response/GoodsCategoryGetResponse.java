package com.sparta.ticketauction.domain.goods.response;

import com.sparta.ticketauction.domain.goods.entity.GoodsCategory;

import lombok.Getter;

@Getter
public class GoodsCategoryGetResponse {
	private final String categoryName;

	public GoodsCategoryGetResponse(GoodsCategory goodsCategory) {
		this.categoryName = goodsCategory.getName();
	}
}
