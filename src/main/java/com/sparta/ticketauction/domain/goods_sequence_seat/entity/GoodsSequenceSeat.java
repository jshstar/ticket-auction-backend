package com.sparta.ticketauction.domain.goods_sequence_seat.entity;

import org.hibernate.annotations.Comment;

import com.sparta.ticketauction.domain.admin.request.GoodsSequenceSeatRequest;
import com.sparta.ticketauction.domain.seat.entity.Seat;
import com.sparta.ticketauction.domain.sequence.entity.Sequence;
import com.sparta.ticketauction.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
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
	private Long price;

	@Comment("판매 타입 - 경매, 일반")
	@Column(name = "sell_type")
	private String sellType;

	@Comment("판매 여부")
	@Column(name = "is_selled")
	private boolean isSelled = false;

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

	public static GoodsSequenceSeat generalOf(
		Seat seat,
		Sequence sequence,
		GoodsSequenceSeatRequest goodsSequenceSeatRequest
	) {
		return new GoodsSequenceSeat(
			seat,
			sequence,
			goodsSequenceSeatRequest.getGeneralAuctionPrice(),
			"일반",
			false
		);
	}

	public static GoodsSequenceSeat auctionOf(
		Seat seat,
		Sequence sequence,
		GoodsSequenceSeatRequest goodsSequenceSeatRequest
	) {
		return new GoodsSequenceSeat(
			seat,
			sequence,
			goodsSequenceSeatRequest.getGeneralAuctionPrice(),
			"경매",
			false
		);
	}

	private GoodsSequenceSeat(Seat seat, Sequence sequence, Long price, String sellType, boolean isSelled) {
		this.seat = seat;
		this.sequence = sequence;
		this.price = price;
		this.sellType = sellType;
		this.isSelled = isSelled;
	}

}
