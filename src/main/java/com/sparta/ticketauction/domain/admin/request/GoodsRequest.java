package com.sparta.ticketauction.domain.admin.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GoodsRequest {
	@Size(min = 1, max = 30, message = "1~30자 사이로 입력해주세요")
	private final String name;

	@Size(min = 1, max = 150, message = "1~150자 사이로 입력해주세요")
	private final String description;

	@NotBlank(message = "공연 시작일 기입은 필수입니다.")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private final LocalDate startDate;

	@NotBlank(message = "공연 종료일은 필수입니다.")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private final LocalDate endDate;

	@NotNull(message = "연령 입력은 필수입니다.")
	private final Integer ageGrade;

	@NotNull(message = "상영 시간은 필수입니다")
	private final Integer runningTime;

	@Size(min = 1, max = 30, message = "카테고리 입력은 필수입니다.")
	private final String categoryName;

}
