package com.sparta.ticketauction.global.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	/* USER */










	/* GOODS */









	/* PLACE */









	/* AUCTION */









	/* RESERVATION */









	/* GLOBAL */
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "", ""),
	INTERNAL_BAD_REQUEST(HttpStatus.BAD_REQUEST, "", "");

	private HttpStatus httpStatus;
	private String code;
	private String message;
}
