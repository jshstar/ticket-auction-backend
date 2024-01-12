package com.sparta.ticketauction.domain.user.service;

import com.sparta.ticketauction.domain.user.request.sms.UserForVerificationRequest;
import com.sparta.ticketauction.domain.user.response.SmsResponse;

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
	 * */
	SmsResponse verifyPhone(UserForVerificationRequest request);

	/*
	 * 토큰 재빌급
	 *
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * */
	void reissue(HttpServletRequest request, HttpServletResponse response);
}