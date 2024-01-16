package com.sparta.ticketauction.domain.goods.response;

import java.time.LocalDate;

import com.sparta.ticketauction.domain.goods.entity.Goods;

import lombok.Getter;

@Getter
public class GoodsGetResponse {
	private final Long goodsId;

	private final LocalDate startDate;

	private final LocalDate endDate;

	private final String placeName;

	private final String placeAddress;

	public GoodsGetResponse(Goods goods) {
		this.goodsId = goods.getId();
		this.startDate = goods.getStartDate();
		this.endDate = goods.getEndDate();
		this.placeName = goods.getPlace().getName();
		this.placeAddress = goods.getPlace().getAddress();
	}
}
