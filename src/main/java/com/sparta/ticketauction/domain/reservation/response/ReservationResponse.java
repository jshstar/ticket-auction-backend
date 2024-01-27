package com.sparta.ticketauction.domain.reservation.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.ticketauction.domain.reservation.entity.ReservationStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class ReservationResponse {

	private Long reservationId; // 예약번호

	private LocalDateTime reservationDate; // 예매일

	private String title; // 상품명

	@JsonFormat(pattern = "yyyy.MM.dd HH:mm")
	private LocalDateTime useDate; // 이용일

	private Integer numberOfTicket; // 매수

	private Long price; // 가격

	@JsonFormat(pattern = "yyyy.MM.dd HH:mm")
	private LocalDateTime cancelDeadline; // 취소가능일

	private ReservationStatus status; // 상태
}
