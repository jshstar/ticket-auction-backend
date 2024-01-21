package com.sparta.ticketauction.domain.goods.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sparta.ticketauction.domain.goods.entity.Goods;

public interface GoodsRepository extends JpaRepository<Goods, Long>, GoodsRepositoryCustom {

	@Query("select g from Goods g "
		+ "left join fetch g.goodsInfo gi "
		+ "left join fetch gi.goodsCategory gc "
		+ "where (:categoryName is null or gc.name = :categoryName) AND (g.endDate > now())")
	Slice<Goods> findAllByGoodsAndCategoryName(Pageable pageable, @Param("categoryName") String categoryName);

}
