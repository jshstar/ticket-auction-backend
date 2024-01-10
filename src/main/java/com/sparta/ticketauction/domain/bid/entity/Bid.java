package com.sparta.ticketauction.domain.bid.entity;

import org.hibernate.annotations.Comment;

import com.sparta.ticketauction.domain.auction.entity.Auction;
import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "bid")
@Entity
public class Bid extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("입찰 금액")
	@Column(name = "price", nullable = false, updatable = false)
	private Long price;

	@Comment("입찰한 유저")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Comment("경매")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auction_id")
	private Auction auction;

	@Builder
	private Bid(Long price, User user, Auction auction) {
		this.price = price;
		this.user = user;
		this.auction = auction;
	}
}
