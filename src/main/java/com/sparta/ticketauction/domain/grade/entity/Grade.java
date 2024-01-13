package com.sparta.ticketauction.domain.grade.entity;

import org.hibernate.annotations.Comment;

import com.sparta.ticketauction.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "grade")
public class Grade extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("좌석등급명")
	@Column(name = "name", length = 30, nullable = false)
	private String name;

	@Comment("일반가격")
	@Column(name = "normal_price", nullable = false)
	private Long normalPrice;

	@Comment("경매가격")
	@Column(name = "auctionPrice", nullable = false)
	private Long auctionPrice;

	@Builder
	public Grade(String name, Long normalPrice, Long auctionPrice) {
		this.name = name;
		this.normalPrice = normalPrice;
		this.auctionPrice = auctionPrice;
	}
}
