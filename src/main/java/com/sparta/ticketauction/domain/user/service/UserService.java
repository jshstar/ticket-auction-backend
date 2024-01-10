package com.sparta.ticketauction.domain.user.service;

import com.sparta.ticketauction.domain.user.request.UserCreateRequest;

public interface UserService {

	/*
	 * 회원 가입 메서드
	 *
	 * @param UserCreateRequest 회원 가입 시, 입력 정보
	 * @return void
	 * */
	void signup(UserCreateRequest request);

	/*
	 * 휴대폰 번호 중복 검사 메서드
	 *
	 * @param String phoneNumber
	 * @return boolean
	 * */
	boolean isExistedPhoneNumber(String phoneNumber);
}
