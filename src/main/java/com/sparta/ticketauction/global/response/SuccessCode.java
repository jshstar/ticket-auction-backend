package com.sparta.ticketauction.global.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {

	/* USER */
	SUCCESS_USER_SIGN_UP(HttpStatus.CREATED, "U00000", "회원 가입에 성공했습니다.");









	/* GOODS */









	/* PLACE */









	/* AUCTION */









	/* RESERVATION */









	/* GLOBAL */

	private HttpStatus httpStatus;
	private String code;
	private String message;
}
