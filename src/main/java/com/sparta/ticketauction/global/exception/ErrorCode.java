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
	EXISTED_USER_PHONE_NUMBER(HttpStatus.CONFLICT, "U10002", "사용 중인 전화 번호 입니다."),
	INVALID_VERIFICATION_NUMBER(HttpStatus.BAD_REQUEST, "U10003", "잘못된 인증 번호 입니다."),
	EXCEED_VERIFICATION_TIME(HttpStatus.BAD_REQUEST, "U10004", "인증 번호 입력 시간 초과 입니다."),
	INVALID_TOKEN(HttpStatus.BAD_REQUEST, "U10100", "잘못된 토큰입니다."),
	NOT_FOUND_USER_FOR_LOGIN(HttpStatus.NOT_FOUND, "U10101", "존재하지 않는 회원입니다."),
	REQUIRED_ADMIN_USER_AUTHORITY(HttpStatus.UNAUTHORIZED, "U19900", "관리자 권한이 필요합니다."),


	/* GOODS */









	/* PLACE */









	/* AUCTION */
	NOT_FOUND_AUCTION(HttpStatus.NOT_FOUND, "A10000", "해당하는 경매를 찾지 못했습니다."),
	ENDED_AUCTION(HttpStatus.BAD_REQUEST, "A10001", "경매가 종료되었습니다."),
	BAD_REQUEST_BID(HttpStatus.BAD_REQUEST, "A10100", "현재 입찰가보다 5% 이상이어야합니다."),








	/* RESERVATION */
	ALREADY_RESERVED_SEAT(HttpStatus.CONFLICT, "R10000", "이미 예약된 좌석입니다."),
	INVALID_SEAT_PRICE(HttpStatus.BAD_REQUEST, "R10001", "좌석 가격이 올바르지 않습니다."),

	/* PAYMENT */
	NOT_ENOUGH_POINT(HttpStatus.BAD_REQUEST, "", "결제할 포인트가 부족합니다"),

	/* GLOBAL */
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "", ""),
	INTERNAL_BAD_REQUEST(HttpStatus.BAD_REQUEST, "", ""),

	/* TOKEN */
	INVALID_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "T10000", "유효하지 않는 JWT 토큰입니다."),
	EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "T10001", "만료된 JWT 토큰입니다."),
	UNSUPPORTED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "T10002", "지원하지 않는 JWT 토큰입니다."),
	NON_ILLEGAL_ARGUMENT_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "T10003", "잘못된 JWT 토큰입니다.");

	private HttpStatus httpStatus;
	private String code;
	private String message;
}
