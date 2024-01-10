package com.sparta.ticketauction.global.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {

	/* USER */
	SUCCESS_USER_SIGN_UP(HttpStatus.CREATED, "U00000", "회원 가입에 성공했습니다."),
	SUCCESS_USER_LOGIN(HttpStatus.OK, "U00100", "로그인에 성공했습니다."),
	SUCCESS_USER_LOGOUT(HttpStatus.OK, "U00200", "로그아웃에 성공했습니다"),
	SUCCESS_SEND_VERIFICATION_NUMBER_BY_SMS(HttpStatus.OK, "U000001", "인증 번호를 발송했습니다"),






	/* GOODS */









	/* PLACE */
	SUCCESS_PLACE_CREATE(HttpStatus.CREATED, "P09900", "공연장이 추가되었습니다."),








	/* AUCTION */
	SUCCESS_BID(HttpStatus.CREATED, "A00100", "입찰에 성공했습니다."),








	/* RESERVATION */
	SUCCESS_RESERVE(HttpStatus.CREATED, "R00000", "예매 성공했습니다."),

	/* GLOBAL */
	OK(HttpStatus.OK, "", "성공");

	private HttpStatus httpStatus;
	private String code;
	private String message;
}
