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

	public void saveAllGoodsSequenceSeat(List<GoodsSequenceSeat> goodsSequenceSeatList) {
		goodsSequenceSeatRepository.saveAll(goodsSequenceSeatList);
	}

	public List<GoodsSequenceSeat> findAllBySequenceId(Long sequenceId) {
		return goodsSequenceSeatRepository.findAllBySequenceId(sequenceId);
	}

}
