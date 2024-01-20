package com.sparta.ticketauction.domain.user.service;

import java.util.Date;

import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.request.sms.UserForVerificationRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

	/*
	 * 로그아웃
	 *
	 * @param HttpServletRequest
	 * @return void
	 * */
	void logout(HttpServletRequest request);

	/*
	 * 핸드폰 번호 인증
	 *
	 * @param UserForSmsRequest 인증 번호를 받을 전화번호
	 * @return 인증번호 만료 시간
	 * */
	Date verifyPhone(UserForVerificationRequest request);

	/*
	 * 토큰 재빌급
	 *
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * */
	void reissue(HttpServletRequest request, HttpServletResponse response);

	/*
	 * 프론트에서 유저 상태 요청 API 호출 시, 포인트 정보도 함께 보내주기 위한 메서드
	 *
	 * @param User 로그인 유저 정보
	 *
	 * @return Long 현재 유저의 포인트 값
	 * */
	Long findPoint(User user);
}