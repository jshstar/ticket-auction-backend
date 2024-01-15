package com.sparta.ticketauction.domain.goods.service;

import static com.sparta.ticketauction.global.exception.ErrorCode.*;

import org.springframework.stereotype.Service;

import com.sparta.ticketauction.domain.admin.request.GoodsRequest;
import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;
import com.sparta.ticketauction.domain.goods.repository.GoodsRepository;
import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.global.exception.ApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

	public final GoodsRepository goodsRepository;

	public Goods createGoods(GoodsRequest goodsRequest, Place place, GoodsInfo goodsInfo) {
		Goods goods = goodsRequest.toGoodsEntity(place, goodsInfo);

		return goodsRepository.save(goods);
	}

	public Goods findById(Long goodsId) {
		return goodsRepository.findById(goodsId)
			.orElseThrow(() -> new ApiException(NOT_FOUND_GOODS));
	}
}
