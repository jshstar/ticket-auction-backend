package com.sparta.ticketauction.domain.admin.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GoodsRequest {
	@Size(max = 30)
	@NotBlank
	private final String name;

	@Size(max = 150)
	@NotBlank
	private final String description;

	@NotBlank
	@JsonFormat(pattern = "yyyy-MM-dd")
	private final LocalDate startDate;

	@NotBlank
	@JsonFormat(pattern = "yyyy-MM-dd")
	private final LocalDate endDate;

	@NotBlank
	private final Integer ageGrade;

	@NotBlank
	private final Integer runningTime;

	@Size(max = 30)
	@NotBlank
	private final String categoryName;

}
