package com.sparta.ticketauction.domain.auction.request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.sparta.ticketauction.domain.auction.entity.Auction;
import com.sparta.ticketauction.domain.grade.entity.ZoneGrade;
import com.sparta.ticketauction.domain.schedule.entity.Schedule;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AuctionCreateRequest {

	@NotNull(message = "경매 좌석 번호를 입력해주세요.")
	private final Integer seatNumber;

	@JsonCreator
	public AuctionCreateRequest(Integer seatNumber) {
		this.seatNumber = seatNumber;
	}

	// TODO: 1/12/24 경매시작일시, 마감일시 포맷 생각하기
	public Auction toEntity(Schedule schedule, ZoneGrade zoneGrade) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
		String now = LocalDateTime.now().format(format);

		return Auction.builder()
			.schedule(schedule)
			.zoneGrade(zoneGrade)
			.seatNumber(seatNumber)
			.startPrice(zoneGrade.getGrade().getAuctionPrice())
			.startDateTime(LocalDateTime.parse(now, format))
			.endDateTime(schedule.getStartDateTime().minusDays(3))
			.build();
	}
}
