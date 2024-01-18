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
	SUCCESS_SEND_VERIFICATION_NUMBER_BY_SMS(HttpStatus.OK, "U00300", "인증 번호를 발송했습니다"),
	SUCCESS_UPDATE_USER_INFO(HttpStatus.OK, "U00400", "회원 정보 수정에 성공했습니다."),
	SUCCESS_UPDATE_USER_PASSWORD(HttpStatus.OK, "U00401", "비밀 번호 변경에 성공했습니다."),
	SUCCESS_GET_USER_INFO(HttpStatus.OK, "U00500", "회원 정보 조회에 성공했습니다."),
	SUCCESS_DELETE_USER(HttpStatus.OK, "U00600", "회원 탈퇴에 성공했습니다."),

	/* GOODS */
	SUCCESS_GET_ALL_GOODS_INFO(HttpStatus.OK, "G00000", "공연 정보 전체 조회 성공했습니다."),
	SUCCESS_GET_SLICE_GOODS(HttpStatus.OK, "G00100", "공연 페이징 조회 성공했습니다."),
	SUCCESS_GET_GOODS(HttpStatus.OK, "G00200", "공연 단건 조회 성공했습니다."),

	/* PLACE */
	SUCCESS_GET_ALL_PLACE(HttpStatus.OK, "P00000", "공연장 전체 조회 성공했습니다."),

	/* GRADE */
	SUCCESS_GET_ALL_GRADE(HttpStatus.OK, "F00000", "등급 전체 조회를 성공했습니다."),

	/* ZONE */
	SUCCESS_GET_ALL_ZONE(HttpStatus.OK, "Z00000", "구역 전체 조회 성공했습니다."),

	/* SCHEDULE */
	SUCCESS_GET_ALL_SCHEDULE(HttpStatus.OK, "S00000", "전 회차 조회 성공했습니다."),

	/* AUCTION */
	SUCCESS_GET_AUCTION_INFO(HttpStatus.OK, "A00500", "경매 정보 조회에 성공했습니다."),
	SUCCESS_GET_ALL_JOINED_AUCTION(HttpStatus.OK, "A00600", "참가한 경매 목록 조회에 성공했습니다."),

	/* BID */
	SUCCESS_GET_ALL_BID(HttpStatus.OK, "B00000", "입찰 내역 조회에 성공했습니다."),
	SUCCESS_BID(HttpStatus.CREATED, "B00100", "입찰에 성공했습니다."),








	/* GOODS_SEQUENCE_SEAT */




	/* POINT */
	SUCCESS_GET_CHARGE_POINT_LOG_LIST(HttpStatus.OK, "P00000", "포인트 충전 내역 조회에 성공했습니다."),
	SUCCESS_GET_CHANGING_POINT_LOG_LIST(HttpStatus.OK, "P00001", "포인트 변동 내역 조회에 성공했습니다."),
	/* ADMIN */
	SUCCESS_GOODS_AND_SCHEDULE_CREATE(HttpStatus.CREATED, "M09900", "공연 및 회차 생성을 성공했습니다."),
	SUCCESS_PLACE_AND_ZONE_CREATE(HttpStatus.CREATED, "M09901", "공연장 및 구역 생성을 성공했습니다."),
	SUCCESS_GRADE_CREATE(HttpStatus.CREATED, "M09902", "등급 생성 성공했습니다."),
	SUCCESS_ZONE_GRADE_CREATE(HttpStatus.CREATED, "M09903", "구역 등급 생성 성공했습니다."),
	SUCCESS_AUCTION_CREATE(HttpStatus.CREATED, "M09904", "경매 생성 성공했습니다."),
	SUCCESS_GOODS_INFO_CREATE(HttpStatus.CREATED, "M09905", "공연정보 생성 성공했습니다."),

	/* RESERVATION */
	SUCCESS_RESERVE(HttpStatus.CREATED, "R00000", "예매 성공했습니다."),

	/* TOKEN */
	SUCCESS_REISSUE_TOKEN(HttpStatus.CREATED, "T00000", "토큰 재발급에 성공했습니다."),

	/* GLOBAL */
	OK(HttpStatus.OK, "", "성공");

	private HttpStatus httpStatus;
	private String code;
	private String message;
}
