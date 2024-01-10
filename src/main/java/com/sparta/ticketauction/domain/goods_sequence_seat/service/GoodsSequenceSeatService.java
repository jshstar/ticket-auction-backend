package com.sparta.ticketauction.domain.goods_sequence_seat.service;

import java.util.List;

import com.sparta.ticketauction.domain.goods_sequence_seat.entity.GoodsSequenceSeat;

public interface GoodsSequenceSeatService {

	// 공연 회차 별 좌석 저장
	void saveAllGoodsSequenceSeat(List<GoodsSequenceSeat> goodsSequenceSeatList);

	// 회차별 공연 조회
	List<GoodsSequenceSeat> findAllBySequenceId(Long sequenceId);

}
