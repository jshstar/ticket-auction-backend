package com.sparta.ticketauction.global.jwt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.ticketauction.global.exception.ApiException;
import com.sparta.ticketauction.global.response.ApiResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (ApiException e) {
			setExceptionResponse(response, e);
		}
	}

	private void setExceptionResponse(HttpServletResponse response, ApiException e) throws IOException {
		response.setStatus(e.getHttpStatus().value());

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());

		String result = mapper.writeValueAsString(
			ApiResponse.of(e.getCode(), e.getMessage(), "{}")
		);

		response.getWriter().write(result);
	}
}
