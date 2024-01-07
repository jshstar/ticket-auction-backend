package com.sparta.ticketauction.global.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

	/*
	 * IllegalArgumentException 에 대한 handler
	 * */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponse<EmptyObject>> illegalArgumentExceptionHandler(
		IllegalArgumentException exception
	) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ApiResponse.of(ErrorCode.INTERNAL_BAD_REQUEST.getCode(), exception.getMessage()));
	}

	/*
	 * MethodArgumentNotValidException 에 대한 handler
	 * */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> methodArgumentNotValidHandler(MethodArgumentNotValidException exception) {
		BindingResult bindingResult = exception.getBindingResult();
		Map<String, String> errors = new HashMap<>();
		bindingResult.getAllErrors().forEach(error ->
			errors.put(((FieldError)error).getField(), error.getDefaultMessage()));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ApiResponse.of(
				ErrorCode.INTERNAL_BAD_REQUEST.getCode(),
				ErrorCode.INTERNAL_BAD_REQUEST.getMessage(),
				errors
			));
	}
}
