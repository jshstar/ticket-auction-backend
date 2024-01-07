package com.sparta.ticketauction.domain.goods_sequence_seat.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsSequenceSeatID implements Serializable {
	private Long seatId;

	private Long sequenceId;
}
