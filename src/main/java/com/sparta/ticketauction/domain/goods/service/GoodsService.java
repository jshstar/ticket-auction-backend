package com.sparta.ticketauction.domain.goods.service;

import java.util.List;

import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.goods.entity.GoodsCategory;
import com.sparta.ticketauction.domain.goods.entity.GoodsImage;

public interface GoodsService {

	// 공연 저장
	Goods saveGoods(Goods goods);

	// 모든 이미지 저장
	List<GoodsImage> saveAllGoodsImage(List<GoodsImage> fileUrl);

	// 이미지 저장
	GoodsImage saveGoodsImage(GoodsImage fileUrl);

	// 공연 카테고리 이름 탐색
	GoodsCategory findGoodsCategory(String category);

	// 공연 카테고리 저장
	GoodsCategory saveGoodSCategory(GoodsCategory category);
}
