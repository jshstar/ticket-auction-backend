package com.sparta.ticketauction.domain.reservation.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponse {

	private Long reservationId;

	private String username;

	private LocalDateTime reservationDate;

	private String goodsTitle;

	private LocalDateTime goodsDateTime;

	private String status;
}
