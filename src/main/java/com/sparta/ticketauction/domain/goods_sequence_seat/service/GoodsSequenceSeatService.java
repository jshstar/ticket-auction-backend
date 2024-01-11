package com.sparta.ticketauction.domain.goods_sequence_seat.service;

import java.util.List;

import com.sparta.ticketauction.domain.goods_sequence_seat.entity.GoodsSequenceSeat;
import com.sparta.ticketauction.domain.goods_sequence_seat.entity.SellType;

public interface GoodsSequenceSeatService {

	// 공연 회차 별 좌석 저장
	void saveAllGoodsSequenceSeat(List<GoodsSequenceSeat> goodsSequenceSeatList);

	// // 공연 회차별 타입 좌석 조회
	List<GoodsSequenceSeat> findAllBySequenceIdAndSellType(Long sequenceId, SellType sellType);

}
