package com.sparta.ticketauction.domain.goods.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class GoodsCategoryGetResponse {
	private final String categoryName;

	@JsonCreator
	public GoodsCategoryGetResponse(@JsonProperty("goodsCategory") String goodsCategory) {
		this.categoryName = goodsCategory;
	}
}
