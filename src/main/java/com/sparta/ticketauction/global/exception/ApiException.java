package com.sparta.ticketauction.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

	private HttpStatus httpStatus;
	private String code;
	private String message;

	public ApiException(ErrorCode errorCode) {
		this.httpStatus = errorCode.getHttpStatus();
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
	}

}
