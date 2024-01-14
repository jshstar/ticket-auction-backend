package com.sparta.ticketauction.domain.grade.service;

import org.springframework.stereotype.Service;

import com.sparta.ticketauction.domain.admin.request.GradeRequest;
import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.grade.entity.Grade;
import com.sparta.ticketauction.domain.grade.repository.GradeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

	private final GradeRepository gradeRepository;

	// 등급 생성
	public void createGrade(GradeRequest gradeRequest, Goods goods) {
		Grade grade = gradeRequest.toEntity(goods);
		gradeRepository.save(grade);
	}

}
