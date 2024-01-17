package com.sparta.ticketauction.domain.goods.service;

import com.sparta.ticketauction.domain.admin.request.GoodsCreateRequest;
import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;
import com.sparta.ticketauction.domain.place.entity.Place;

public interface GoodsService {

	// 공연 생성
	Goods createGoods(GoodsCreateRequest goodsCreateRequest, Place place, GoodsInfo goodsInfo);

	Goods findById(Long goodsId);
}
