package com.sparta.ticketauction.domain.goods.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.goods.entity.GoodsImage;

public interface GoodsImageRepository extends JpaRepository<GoodsImage, Long> {

	Optional<GoodsImage> findThumbnailByGoodsId(Long goodsId);
}
