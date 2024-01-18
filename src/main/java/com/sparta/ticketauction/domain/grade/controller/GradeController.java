package com.sparta.ticketauction.domain.grade.controller;

import static com.sparta.ticketauction.global.response.SuccessCode.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.ticketauction.domain.grade.response.GradeGetResponse;
import com.sparta.ticketauction.domain.grade.service.GradeService;
import com.sparta.ticketauction.domain.grade.service.ZoneGradeService;
import com.sparta.ticketauction.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class GradeController {

	private final GradeService gradeService;

	private final ZoneGradeService zoneGradeService;

	// 해당 공현의 등급 전체 정보 조회
	@GetMapping("/goods/{goodsId}/grade")
	public ResponseEntity<ApiResponse<List<GradeGetResponse>>> getAllGrade(@PathVariable Long goodsId) {
		List<GradeGetResponse> gradeGetResponses = gradeService.getAllGrade(goodsId);
		return ResponseEntity
			.status(
				SUCCESS_GET_ALL_GRADE.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_GET_ALL_GRADE.getCode(),
					SUCCESS_GET_ALL_GRADE.getMessage(),
					gradeGetResponses)
			);
	}
}
