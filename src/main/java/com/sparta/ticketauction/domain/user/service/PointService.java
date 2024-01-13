package com.sparta.ticketauction.domain.user.service;

import com.sparta.ticketauction.domain.user.entity.User;

public interface PointService {

	/*
	 * 포인트 사용
	 *
	 * @param user	포인트를 사용하는 회원
	 * @param point	사용할 포인트 양
	 * */
	void usePoint(User user, Long point);

	/*
	 * 포인트 충전
	 *
	 * @param user	포인트를 충전하는 회원
	 * @param point	충전할 포인트 양
	 * */
	void chargePoint(User user, Long point);
}
