package com.sparta.ticketauction.domain.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;

public interface GoodsInfoRepository extends JpaRepository<GoodsInfo, Long> {
}
