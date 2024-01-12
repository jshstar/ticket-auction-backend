package com.sparta.ticketauction.domain.user.service;

import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.request.UserCreateRequest;
import com.sparta.ticketauction.domain.user.request.UserUpdateRequest;
import com.sparta.ticketauction.domain.user.response.UserResponse;

public interface UserService {

	/*
	 * 회원 가입 메서드
	 *
	 * @param UserCreateRequest 회원 가입 시, 입력 정보
	 * @return void
	 * */
	UserResponse signup(UserCreateRequest request);

	/*
	 * 휴대폰 번호 중복 검사 메서드
	 *
	 * @param String phoneNumber
	 * @return boolean
	 * */
	boolean isExistedPhoneNumber(String phoneNumber);

	/*
	 * 해당 id 값의 유저 찾기 메서드
	 *
	 * @param userId 찾을 유저의 id
	 * @return User  해당 id를 가진 유저 객체
	 * */
	User findByUserId(Long userId);

	/*
	 * 유저 정보 수정 - 입력이 되고, 검증된 값에 한하여 수정됨
	 *
	 * @param user 		로그인 한 유저 정보
	 * @param userId 	정보가 수정될 유저의 id
	 * @param request	변경할 정보
	 * */
	UserResponse updateUserInfo(User loginUser, Long userId, UserUpdateRequest request);

	/*
	 * 유저 정보 조회
	 *
	 * @param user	 		로그인 한 융저 정보
	 * @param userId 		조회할 유저의 id
	 *
	 * @return UserResponse	유저 응답 정보 dto
	 * */
	UserResponse getUserInfo(User user, Long userId);
}
