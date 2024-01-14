package com.sparta.ticketauction.domain.admin.request;

import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.grade.entity.Grade;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GradeRequest {
	@Size(min = 1, max = 30, message = "1~30자 사이로 입력해주세요.")
	private final String name;

	@NotNull(message = "일반 가격 정보는 필수 입니다.")
	private final Long normalPrice;

	@NotNull(message = "경매 가격 정보는 필수 입니다.")
	private final Long auctionPrice;

	public Grade toEntity(Goods goods) {
		return Grade
			.builder()
			.name(this.name)
			.normalPrice(normalPrice)
			.auctionPrice(auctionPrice)
			.goods(goods)
			.build();
	}
}
