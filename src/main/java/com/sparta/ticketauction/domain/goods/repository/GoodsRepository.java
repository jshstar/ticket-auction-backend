package com.sparta.ticketauction.domain.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.goods.entity.Goods;

public interface GoodsRepository extends JpaRepository<Goods, Long>, GoodsRepositoryCustom {

}
