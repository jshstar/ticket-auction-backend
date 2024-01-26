package com.sparta.ticketauction.domain.reservation.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.ticketauction.domain.reservation.dto.ReservationSeatInfo;

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

	private String username; // 예매자

	private String title; // 제목

	@JsonFormat(pattern = "yyyy.MM.dd HH:mm")
	private LocalDateTime useDate; // 이용일

	private String address; // 공연 장소

	private List<ReservationSeatInfo> seats; // 좌석 정보
}
