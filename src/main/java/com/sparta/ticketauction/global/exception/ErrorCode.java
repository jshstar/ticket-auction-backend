package com.sparta.ticketauction.global.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	/* USER */
	// 00: not found, 99 : 관리자 관련
	NOT_FOUND_BY_ID(HttpStatus.NOT_FOUND, "U10000", "해당 id를 가지는 유저를 찾을 수 없습니다."),
	NOT_FOUND_USER_FOR_LOGIN(HttpStatus.NOT_FOUND, "U10001", "존재하지 않는 회원입니다."),
	REQUIRED_ADMIN_USER_AUTHORITY(HttpStatus.UNAUTHORIZED, "U19900", "관리자 권한이 필요합니다."),
	// 검증 실패 XXABXX (AB : 01 이메일, 02: 비밀번호, 03: 이름, 04:닉네임, 05: 생일, 06: 전화번호, 07: 검증번호),
	EXISTED_USER_EMAIL(HttpStatus.CONFLICT, "U10100", "사용 중인 이메일 입니다."),
	ALREADY_USED_PASSWORD(HttpStatus.BAD_REQUEST, "U10200", "기존과 다른 비밀 번호를 입력해주세요."),
	EXISTED_USER_NICKNAME(HttpStatus.CONFLICT, "U10400", "사용 중인 닉네임 입니다."),
	INVALID_NICKNAME_LENGTH(HttpStatus.BAD_REQUEST, "U10401", "최소 2자, 최대 10자로 입력해주세요."),
	INVALID_NICKNAME_PATTERN(HttpStatus.BAD_REQUEST, "U10402", "한글로만 입력해주세요."),
	EXISTED_USER_PHONE_NUMBER(HttpStatus.CONFLICT, "U10600", "사용 중인 전화 번호 입니다."),
	INVALID_PHONE_NUMBER_PATTERN(HttpStatus.BAD_REQUEST, "U10601", "전화번호 형식으로, 숫자로만 입력해주세요."),
	INVALID_VERIFICATION_NUMBER(HttpStatus.BAD_REQUEST, "U10700", "잘못된 인증 번호 입니다."),
	EXCEED_VERIFICATION_TIME(HttpStatus.BAD_REQUEST, "U10701", "인증 번호 입력 시간 초과 입니다."),

	/* GOODS */
	NOT_FOUND_GOODS(HttpStatus.NOT_FOUND, "G10000", "해당하는 공연이 없습니다."),
	NOT_FOUND_GOODS_INFO(HttpStatus.NOT_FOUND, "G10001", "해당하는 공연 정보가 없습니다."),

	/* SCHEDULE */
	NOT_FOUND_SCHEDULE(HttpStatus.NOT_FOUND, "S10000", "해당하는 회차가 없습니다."),

	/* PLACE */
	NOT_FOUND_PLACE(HttpStatus.NOT_FOUND, "P10000", "해당하는 공연장이 없습니다."),

	/* AUCTION */
	NOT_FOUND_AUCTION(HttpStatus.NOT_FOUND, "A10000", "해당하는 경매를 찾지 못했습니다."),
	ENDED_AUCTION(HttpStatus.BAD_REQUEST, "A10001", "경매가 종료되었습니다."),

	/* BID */
	NOT_FOUND_BID(HttpStatus.NOT_FOUND, "B10000", "입찰을 찾지 못했습니다."),
	NOT_FOUND_WIN_BID(HttpStatus.NOT_FOUND, "B10001", "낙찰자를 찾지 못했습니다."),
	BAD_REQUEST_BID(HttpStatus.BAD_REQUEST, "B10100", "현재 입찰가보다 5% 이상이어야합니다."),

	/* RESERVATION */
	ALREADY_RESERVED_SEAT(HttpStatus.CONFLICT, "R10000", "이미 예약된 좌석입니다."),
	INVALID_SEAT_PRICE(HttpStatus.BAD_REQUEST, "R10001", "좌석 가격이 올바르지 않습니다."),

	/* PAYMENT */
	NOT_ENOUGH_POINT(HttpStatus.BAD_REQUEST, "P10000", "결제할 포인트가 부족합니다"),
	NOT_FOUND_ORDER_ID(HttpStatus.NOT_FOUND, "P10100", "해당 id를 가진 결제 요청이 없습니다."),
	NOT_EQUALS_AMOUNT(HttpStatus.BAD_REQUEST, "P10101", "결제 금액과 요청 결제 금액이 일치하지 않습니다."),

	/* GLOBAL */
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "", ""),
	INTERNAL_BAD_REQUEST(HttpStatus.BAD_REQUEST, "", ""),
	ACCESS_DENIED(HttpStatus.FORBIDDEN, "G10000", "해당 요청에 대한 권한이 없습니다."),

	/* TOKEN */
	INVALID_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "T10000", "유효하지 않는 JWT 토큰입니다."),
	EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "T10001", "만료된 JWT 토큰입니다."),
	UNSUPPORTED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "T10002", "지원하지 않는 JWT 토큰입니다."),
	NON_ILLEGAL_ARGUMENT_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "T10003", "잘못된 JWT 토큰입니다.");

	private HttpStatus httpStatus;
	private String code;
	private String message;
}
