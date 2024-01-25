package com.sparta.ticketauction.domain.goods.repository;

import java.util.List;

import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.seat.response.AuctionSeatInfoResponse;
import com.sparta.ticketauction.domain.seat.response.SeatInfoResponse;

public interface GoodsRepositoryCustom {

	List<SeatInfoResponse> findGoodsSeatInfo(Long goodsId);

	List<AuctionSeatInfoResponse> findGoodsAuctionSeatInfo(Long scheduleId, Long goodsId);

	List<Goods> findAllByGoodsAndCategoryName(Long cursorId, int size, String categoryName);
}
