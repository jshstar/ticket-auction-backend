package com.sparta.ticketauction.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sparta.ticketauction.global.dto.EmptyObject;
import com.sparta.ticketauction.global.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	/*
	 * ApiException 에 대한 handler
	 * */
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse<EmptyObject>> apiExceptionHandler(ApiException exception) {
		return ResponseEntity.status(exception.getHttpStatus())
			.body(ApiResponse.of(exception.getCode(), exception.getMessage()));
	}

	/*
	 * RuntimeException 에 대한 handler
	 * */
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiResponse<EmptyObject>> runtimeExceptionHandler(RuntimeException exception) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(ApiResponse.of(ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
				ErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
	}
}
