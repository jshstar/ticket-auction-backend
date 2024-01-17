package com.sparta.ticketauction.domain.admin.request;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;
import com.sparta.ticketauction.domain.place.entity.Place;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GoodsCreateRequest {
	@NotBlank(message = "공연제목 기입은 필수 입니다.")
	@Pattern(regexp = "(\\S+)-(\\S+)", message = "입력 양식: (공연제목)-(지역 및 목표) 입니다.")
	private final String title;

	@NotNull(message = "공연 시작일 기입은 필수입니다.")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private final LocalDate startDate;

	@NotNull(message = "공연 종료일은 필수입니다.")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private final LocalDate endDate;

	@NotNull(message = "상영 시간은 필수입니다")
	@JsonFormat(pattern = "HH:mm")
	private final LocalTime startTime;

	public Goods toEntity(Place place, GoodsInfo goodsInfo) {
		return Goods
			.builder()
			.title(title)
			.place(place)
			.goodsInfo(goodsInfo)
			.startDate(this.startDate)
			.endDate(this.endDate)
			.build();
	}

}
