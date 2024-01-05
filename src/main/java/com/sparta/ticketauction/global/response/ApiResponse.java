package com.sparta.ticketauction.global.response;

import org.springframework.http.ResponseEntity;

import com.sparta.ticketauction.global.dto.EmptyObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

	private String code;
	private String message;
	private T data;

	public static <K> ApiResponse<K> of(String code, String message, K data) {
		return new ApiResponse<>(code, message, data);
	}

	public static  ApiResponse<EmptyObject> of(String code, String message) {
		EmptyObject emptyObject = EmptyObject.get();
		return new ApiResponse<>(code, message, emptyObject);
	}
}
