package com.sparta.ticketauction.domain.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.goods.entity.GoodsCategory;

public interface GoodsCategoryRepository extends JpaRepository<GoodsCategory, Long> {
}
