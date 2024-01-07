package com.sparta.ticketauction.domain.auction.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuctionCreateRequest {

	@NotNull(message = "시작가를 입력해주세요.")
	private final Long startPrice;

	@NotBlank(message = "경매 시작일시를 입력해주세요.")
	@JsonFormat(pattern = "yyyy-MM-dd HH")
	private final LocalDateTime startDateTime;

	@NotBlank(message = "경매 마감일시를 입력해주세요.")
	@JsonFormat(pattern = "yyyy-MM-dd HH")
	private final LocalDateTime endDateTime;

}
