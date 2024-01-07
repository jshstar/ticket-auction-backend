package com.sparta.ticketauction.domain.goods_sequence_seat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.goods_sequence_seat.entity.GoodsSequenceSeat;
import com.sparta.ticketauction.domain.goods_sequence_seat.entity.GoodsSequenceSeatID;

public interface GoodsSequenceSeatRepository extends JpaRepository<GoodsSequenceSeat, GoodsSequenceSeatID> {
}
