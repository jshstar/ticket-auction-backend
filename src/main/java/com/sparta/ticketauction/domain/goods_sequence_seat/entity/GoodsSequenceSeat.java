package com.sparta.ticketauction.domain.goods_sequence_seat.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import com.sparta.ticketauction.domain.seat.entity.Seat;
import com.sparta.ticketauction.domain.sequence.entity.Sequence;
import com.sparta.ticketauction.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "goods_sequence_seat")
public class GoodsSequenceSeat extends BaseEntity {

	@EmbeddedId
	private GoodsSequenceSeatID id;

	@Comment("가격")
	@Column(name = "price")
	@ColumnDefault("0")
	private Long price = 0L;

	@Comment("판매 타입 - NORMAL, AUCTION")
	@Column(name = "sell_type")
	@Enumerated(EnumType.STRING)
	private SellType sellType;

	@Comment("판매 여부")
	@Column(name = "is_selled")
	private Boolean isSelled = false;

	@Comment("좌석")
	@MapsId("seatId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seat_id")
	private Seat seat;

	@Comment("회차")
	@MapsId("sequenceId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sequence_id")
	private Sequence sequence;

	@Comment("낙관적 락 버전")
	@Version
	private int version;

	@Builder
	private GoodsSequenceSeat(Seat seat, Sequence sequence, Long price, SellType sellType, boolean isSelled) {
		this.seat = seat;
		this.sequence = sequence;
		this.price = price;
		this.sellType = sellType;
		this.isSelled = isSelled;
		this.id = new GoodsSequenceSeatID(seat.getId(), sequence.getId());
	}

}
