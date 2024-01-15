package com.sparta.ticketauction.domain.goods.service;

import com.sparta.ticketauction.domain.admin.request.GoodsRequest;
import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;
import com.sparta.ticketauction.domain.place.entity.Place;

public interface GoodsService {

	// 공연 생성
	Goods createGoods(GoodsRequest goodsRequest, Place place, GoodsInfo goodsInfo);

}
