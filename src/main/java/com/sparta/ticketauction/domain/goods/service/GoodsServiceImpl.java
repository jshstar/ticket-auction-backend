package com.sparta.ticketauction.domain.goods.service;

import static com.sparta.ticketauction.global.exception.ErrorCode.*;

import org.springframework.stereotype.Service;

import com.sparta.ticketauction.domain.admin.request.GoodsCreateRequest;
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

	@Override
	public Goods createGoods(GoodsCreateRequest goodsCreateRequest, Place place, GoodsInfo goodsInfo) {
		Goods goods = goodsCreateRequest.toEntity(place, goodsInfo);

		return goodsRepository.save(goods);
	}

	public Goods findById(Long goodsId) {
		return goodsRepository.findById(goodsId)
			.orElseThrow(() -> new ApiException(NOT_FOUND_GOODS));
	}
}
