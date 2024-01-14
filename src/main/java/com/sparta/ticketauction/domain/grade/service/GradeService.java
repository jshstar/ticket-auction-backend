package com.sparta.ticketauction.domain.grade.service;

import com.sparta.ticketauction.domain.admin.request.GradeRequest;
import com.sparta.ticketauction.domain.goods.entity.Goods;

public interface GradeService {
	// 등급 생성
	void createGrade(GradeRequest gradeRequest, Goods goods);
}
