package com.sparta.ticketauction.domain.auction.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuctionCreateRequest {

	@NotBlank(message = "시작가를 입력해주세요.")
	private final Long startPrice;

	@JsonFormat(pattern = "yyyy-MM-dd HH")
	private final LocalDateTime startDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH")
	private final LocalDateTime endDate;

}
