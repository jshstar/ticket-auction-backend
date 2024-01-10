package com.sparta.ticketauction.domain.goods.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.goods.entity.GoodsCategory;
import com.sparta.ticketauction.domain.goods.entity.GoodsImage;
import com.sparta.ticketauction.domain.goods.repository.GoodsCategoryRepository;
import com.sparta.ticketauction.domain.goods.repository.GoodsImageRepository;
import com.sparta.ticketauction.domain.goods.repository.GoodsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

	public final GoodsRepository goodsRepository;

	public final GoodsImageRepository goodsImageRepository;

	public final GoodsCategoryRepository goodsCategoryRepository;

	public Goods saveGoods(Goods goods) {
		return goodsRepository.save(goods);
	}

	public List<GoodsImage> saveAllGoodsImage(List<GoodsImage> fileUrl) {
		return goodsImageRepository.saveAll(fileUrl);
	}

	public GoodsImage saveGoodsImage(GoodsImage fileUrl) {
		return goodsImageRepository.save(fileUrl);
	}

	public GoodsCategory findGoodsCategory(String category) {
		return goodsCategoryRepository.findByName(category).orElse(null);
	}

	public GoodsCategory saveGoodSCategory(GoodsCategory category) {
		return goodsCategoryRepository.save(category);
	}

}
