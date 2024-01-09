package com.sparta.ticketauction.global.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	/* USER */
	EXISTED_USER_EMAIL(HttpStatus.CONFLICT, "U10000", "사용 중인 이메일 입니다."),
	EXISTED_USER_NICKNAME(HttpStatus.CONFLICT, "U10001", "사용 중인 닉네임 입니다."),
	INVALID_TOKEN(HttpStatus.BAD_REQUEST, "U10100", "잘못된 토큰입니다."),
	NOT_FOUND_USER_FOR_LOGIN(HttpStatus.NOT_FOUND, "U10101", "존재하지 않는 회원입니다."),
	REQUIRED_LOGIN(HttpStatus.UNAUTHORIZED, "U10102", "로그인이 필요합니다."),






	/* GOODS */









	/* PLACE */
	NOTFOUND_PLACE(HttpStatus.NOT_FOUND, "P10000", "해당하는 공연장이 없습니다."),








	/* AUCTION */









	/* RESERVATION */
	ALREADY_RESERVED_SEAT(HttpStatus.CONFLICT, "R10000", "이미 예약된 좌석입니다."),
	INVALID_SEAT_PRICE(HttpStatus.BAD_REQUEST, "R10001", "좌석 가격이 올바르지 않습니다."),

	/* PAYMENT */
	NOT_ENOUGH_POINT(HttpStatus.OK, "", "결제할 포인트가 부족합니다"),

	/* GLOBAL */
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "", ""),
	INTERNAL_BAD_REQUEST(HttpStatus.BAD_REQUEST, "", ""),
	;

	private HttpStatus httpStatus;
	private String code;
	private String message;
}
