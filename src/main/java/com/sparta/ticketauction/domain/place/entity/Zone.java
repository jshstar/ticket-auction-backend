package com.sparta.ticketauction.domain.place.entity;

import org.hibernate.annotations.Comment;

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
@Entity
@Table(name = "zone")
public class Zone extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("구역명")
	@Column(name = "name", length = 10)
	private String name;

	@Comment("좌석수")
	@Column(name = "seat_number")
	private Integer seatNumber;

	@Comment("공연장")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "palce_id")
	private Place place;

	@Builder
	private Zone(String name, Integer seatNumber, Place place) {
		this.name = name;
		this.seatNumber = seatNumber;
		this.place = place;
	}

}

