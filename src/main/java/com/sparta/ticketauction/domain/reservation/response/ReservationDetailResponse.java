package com.sparta.ticketauction.domain.reservation.response;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReservationDetailResponse {

	private Long reservationId; // 예약 번호

	private String username; // 유저 이름

	private LocalDateTime reservationDate; // 예약일자

	private String goodsTitle; // 공연 제목

	private int sequence; // 회차

	private String zone; // 좌석 구역

	private Integer seatNumber; // 좌석 번호

	private String address; // 예약 장소

	private LocalDateTime goodsStartDateTime; // 이용일자

	private String thumbnailUrl; // 썸네일 이미지

	public ReservationDetailResponse(
		Long reservationId,
		String username,
		LocalDateTime reservationDate,
		String goodsTitle,
		int sequence,
		String zone,
		Integer seatNumber,
		String address,
		LocalDateTime goodsStartDateTime
	) {
		this.reservationId = reservationId;
		this.username = username;
		this.reservationDate = reservationDate;
		this.goodsTitle = goodsTitle;
		this.sequence = sequence;
		this.zone = zone;
		this.seatNumber = seatNumber;
		this.address = address;
		this.goodsStartDateTime = goodsStartDateTime;
	}
}
