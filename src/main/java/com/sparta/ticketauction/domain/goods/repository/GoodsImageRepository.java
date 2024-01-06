package com.sparta.ticketauction.domain.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.goods.entity.GoodsImage;

public interface GoodsImageRepository extends JpaRepository<GoodsImage, Long> {
}
