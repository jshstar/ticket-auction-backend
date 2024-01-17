package com.sparta.ticketauction.domain.grade.service;

import java.util.List;

import com.sparta.ticketauction.domain.admin.request.GradeCreateRequest;
import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.grade.entity.Grade;
import com.sparta.ticketauction.domain.grade.response.GradeGetResponse;

public interface GradeService {
	// 등급 생성
	Grade createGrade(GradeCreateRequest gradeCreateRequest, Goods goods);

	// 해당 공현의 등급 전체 정보 조회
	List<GradeGetResponse> getAllGrade(Long goodsId);

	// 등급 프록시 객체 조회
	Grade getReferenceById(Long gradeId);
}
