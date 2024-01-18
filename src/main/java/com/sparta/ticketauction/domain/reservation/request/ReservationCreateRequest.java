package com.sparta.ticketauction.domain.reservation.request;

import java.util.List;

import com.sparta.ticketauction.domain.reservation.reservation_seat.request.ReservationSeatCreateRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReservationCreateRequest {

	@NotNull(message = "가격을 입력하세요.")
	private Long price;

	@NotEmpty(message = "좌석 정보를 입력하세요.")
	private List<@Valid ReservationSeatCreateRequest> reservationSeats;
}
