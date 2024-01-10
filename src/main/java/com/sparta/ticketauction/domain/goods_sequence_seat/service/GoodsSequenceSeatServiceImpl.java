package com.sparta.ticketauction.domain.goods_sequence_seat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sparta.ticketauction.domain.goods_sequence_seat.entity.GoodsSequenceSeat;
import com.sparta.ticketauction.domain.goods_sequence_seat.repository.GoodsSequenceSeatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoodsSequenceSeatServiceImpl implements GoodsSequenceSeatService {

	private final GoodsSequenceSeatRepository goodsSequenceSeatRepository;

	// 공연 회차 별 좌석 저장
	@Override
	public void saveAllGoodsSequenceSeat(List<GoodsSequenceSeat> goodsSequenceSeatList) {
		goodsSequenceSeatRepository.saveAll(goodsSequenceSeatList);
	}

	// 회차별 공연 조회
	@Override
	public List<GoodsSequenceSeat> findAllBySequenceId(Long sequenceId) {
		return goodsSequenceSeatRepository.findAllBySequenceId(sequenceId);
	}

}
