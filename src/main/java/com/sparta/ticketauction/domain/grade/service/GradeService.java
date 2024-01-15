package com.sparta.ticketauction.domain.grade.service;

import com.sparta.ticketauction.domain.admin.request.GradeRequest;
import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.grade.entity.Grade;

public interface GradeService {
	// 등급 생성
	Grade createGrade(GradeRequest gradeRequest, Goods goods);

	// 등급 프록시 객체 조회
	Grade getReferenceById(Long gradeId);
}
