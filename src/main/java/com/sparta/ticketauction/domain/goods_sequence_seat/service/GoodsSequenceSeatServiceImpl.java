package com.sparta.ticketauction.domain.goods_sequence_seat.service;

import org.springframework.stereotype.Service;

import com.sparta.ticketauction.domain.goods.entity.GoodsCategory;
import com.sparta.ticketauction.domain.goods.repository.GoodsCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoodsSequenceSeatServiceImpl implements GoodsSequenceSeatService {

	private final GoodsCategoryRepository goodsCategoryRepository;

	public GoodsCategory findGoodsCategory(String category) {
		return goodsCategoryRepository.findByName(category).orElse(null);
	}

	public GoodsCategory saveGoodSCategory(GoodsCategory category) {
		return goodsCategoryRepository.save(category);
	}

}
