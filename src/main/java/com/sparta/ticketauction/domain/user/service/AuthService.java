package com.sparta.ticketauction.domain.user.service;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

	/*
	 * 로그아웃
	 *
	 * @param HttpServletRequest
	 * @return void
	 * */
	void logout(HttpServletRequest request);
}
