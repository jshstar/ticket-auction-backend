package com.sparta.ticketauction.domain.auction.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import com.sparta.ticketauction.domain.goods_sequence_seat.entity.GoodsSequenceSeat;
import com.sparta.ticketauction.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "auction")
public class Auction extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("시작가")
	@ColumnDefault("0")
	@Column(name = "start_price", nullable = false)
	private Long startPrice;

	@Comment("입찰가")
	@Column(name = "bid_price", nullable = false)
	private Long bidPrice;
	@Comment("시작일시")
	@Column(name = "start_date", nullable = false)
	private LocalDateTime startDateTime;

	@Comment("마감일시")
	@Column(name = "end_date", nullable = false)
	private LocalDateTime endDateTime;

	@Comment("종료여부 T - 종료 / F - 진행 중")
	@ColumnDefault("false")
	@Column(name = "is_ended")
	private Boolean isEnded = false;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "seat_id", referencedColumnName = "seat_id"),
		@JoinColumn(name = "sequence_id", referencedColumnName = "sequence_id")
	})
	private GoodsSequenceSeat sequenceSeat;

	@Builder
	private Auction(
		Long startPrice,
		LocalDateTime startDateTime,
		LocalDateTime endDateTime,
		GoodsSequenceSeat sequenceSeat
	) {
		this.startPrice = startPrice;
		this.bidPrice = startPrice;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.sequenceSeat = sequenceSeat;
	}

	public void updateBidPrice(Long bidPrice) {
		this.bidPrice = bidPrice;
	}

	public void ended() {
		this.isEnded = true;
	}
}
