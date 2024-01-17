package com.sparta.ticketauction.domain.grade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.grade.entity.Grade;

public interface GradeRepository extends JpaRepository<Grade, Long> {
	// 해당 공현의 등급 전체 조회
	List<Grade> findAllByGoodsId(Long goodsId);
}
