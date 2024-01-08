package com.sparta.ticketauction.domain.auction.entity;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import com.sparta.ticketauction.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	private Long startPrice = 0L;

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

	@OneToMany(mappedBy = "auction")
	private Set<Bid> bid = new LinkedHashSet<>();

	@Builder
	private Auction(Long startPrice, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		this.startPrice = startPrice;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}

	public void updateBidPrice(Long bidPrice) {
		this.bidPrice = bidPrice;
	}
}
