package com.sparta.ticketauction.domain.grade.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.ticketauction.domain.admin.request.GradeCreateRequest;
import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.grade.entity.Grade;
import com.sparta.ticketauction.domain.grade.repository.GradeRepository;
import com.sparta.ticketauction.domain.grade.response.GradeGetResponse;

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

	// 해당 공현의 등급 전체 정보 조회
	@Override
	@Transactional(readOnly = true)
	public List<GradeGetResponse> getAllGrade(Long goodsId) {
		List<Grade> gradeList = gradeRepository.findAllByGoodsId(goodsId);
		return gradeList.stream().map(GradeGetResponse::new).toList();
	}

	// 등급 프록시 객체 조회
	@Override
	public Grade getReferenceById(Long gradeId) {
		return gradeRepository.getReferenceById(gradeId);
	}

}
