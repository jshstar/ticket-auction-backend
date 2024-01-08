package com.sparta.ticketauction.domain.reservation.request;

import com.sparta.ticketauction.domain.goods_sequence_seat.entity.GoodsSequenceSeat;
import com.sparta.ticketauction.domain.reservation.entity.Reservation;
import com.sparta.ticketauction.domain.user.entity.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCreateRequest {

	@NotBlank(message = "구역을 입력하세요.")
	private String zone;

	@NotNull(message = "가격을 입력하세요.")
	private Long price;

	public Reservation toEntity(User user, GoodsSequenceSeat goodsSequenceSeat, Long price) {
		return Reservation.builder()
			.user(user)
			.goodsSequenceSeat(goodsSequenceSeat)
			.price(price)
			.build();
	}
}
