package com.sparta.ticketauction.domain.goods_sequence_seat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.goods_sequence_seat.entity.GoodsSequenceSeat;
import com.sparta.ticketauction.domain.goods_sequence_seat.entity.GoodsSequenceSeatID;
import com.sparta.ticketauction.domain.goods_sequence_seat.entity.SellType;

public interface GoodsSequenceSeatRepository extends JpaRepository<GoodsSequenceSeat, GoodsSequenceSeatID> {

	// 공연 회차별 타입 좌석 조회
	List<GoodsSequenceSeat> findAllBySequenceIdAndSellType(Long sequenceId, SellType sellType);

}
