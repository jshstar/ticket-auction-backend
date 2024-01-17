package com.sparta.ticketauction.domain.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sparta.ticketauction.domain.user.response.PointChargeResponse;

public interface PointRepositoryCustom {
	/*
	 * 해당 유저의 포인트 사용 내역 페이징 리스트 조회
	 *
	 * @param userId      				유저 id
	 * @param pageable    				페이징 정보
	 *
	 * @return Page<PointChargeResponse> 해당 페이지 정보에 맞는 유저의 포인트 사용 내역 리스트
	 * */
	Page<PointChargeResponse> findChargePointListByPage(Long userId, Pageable pageable);
}