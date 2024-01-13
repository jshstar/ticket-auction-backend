package com.sparta.ticketauction.domain.goods.service;

import org.springframework.stereotype.Service;

import com.sparta.ticketauction.domain.admin.request.GoodsRequest;
import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;
import com.sparta.ticketauction.domain.goods.repository.GoodsRepository;
import com.sparta.ticketauction.domain.place.entity.Place;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

	public final GoodsRepository goodsRepository;

	public Goods createGoods(GoodsRequest goodsRequest, Place place, GoodsInfo goodsInfo) {
		Goods goods = goodsRequest.toEntity(place, goodsInfo);

		return goodsRepository.save(goods);
	}
}
