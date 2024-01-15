package com.sparta.ticketauction.domain.goods.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;

public interface GoodsInfoRepository extends JpaRepository<GoodsInfo, Long> {
	// 공연 정보 카테고리별 페이징 페이징 조회 categoryName null일시 전체 조회
	@Query("select g from GoodsInfo g where (:categoryName is null or g.goodsCategory.name = :categoryName)")
	Slice<GoodsInfo> findAllByCategoryName(Pageable pageable, @Param("categoryName") String categoryName);
}
