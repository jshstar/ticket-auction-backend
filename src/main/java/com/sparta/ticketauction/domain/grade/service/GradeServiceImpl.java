package com.sparta.ticketauction.domain.grade.service;

import org.springframework.stereotype.Service;

import com.sparta.ticketauction.domain.admin.request.GradeCreateRequest;
import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.grade.entity.Grade;
import com.sparta.ticketauction.domain.grade.repository.GradeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

	private final GradeRepository gradeRepository;

	// 등급 생성
	@Override
	public Grade createGrade(GradeCreateRequest gradeCreateRequest, Goods goods) {
		Grade grade = gradeCreateRequest.toEntity(goods);
		return gradeRepository.save(grade);
	}

	// 등급 프록시 객체 조회
	@Override
	public Grade getReferenceById(Long gradeId) {
		return gradeRepository.getReferenceById(gradeId);
	}

}
