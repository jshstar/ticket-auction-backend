package com.sparta.ticketauction.domain.goods.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;

public interface GoodsInfoRepository extends JpaRepository<GoodsInfo, Long> {
	@Query("select g from GoodsInfo g where (:categoryName is null or g.goodsCategory.name = :categoryName)")
	Slice<GoodsInfo> findAllByCategoryName(Pageable pageable, @Param("categoryName") String categoryName);
}
