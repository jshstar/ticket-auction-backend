package com.sparta.ticketauction.domain.reservation.entity;

import org.hibernate.annotations.Comment;

import com.sparta.ticketauction.domain.goods_sequence_seat.entity.GoodsSequenceSeat;
import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "reservation")
public class Reservation extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "seat_id", referencedColumnName = "seat_id", nullable = false),
		@JoinColumn(name = "sequence_id", referencedColumnName = "sequence_id", nullable = false)
	})
	private GoodsSequenceSeat goodsSequenceSeat;

	@Comment("유저")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Comment("예매 가격")
	@Column(name = "price", nullable = false)
	private Long price;

	@Comment("예매 상태")
	@Column(name = "status", length = 10, nullable = false)
	@Enumerated(EnumType.STRING)
	private ReservationStatus status;

	@Builder
	private Reservation(User user, GoodsSequenceSeat goodsSequenceSeat, Long price) {
		this.user = user;
		this.goodsSequenceSeat = goodsSequenceSeat;
		this.price = price;
		this.status = ReservationStatus.OK;
	}
}
