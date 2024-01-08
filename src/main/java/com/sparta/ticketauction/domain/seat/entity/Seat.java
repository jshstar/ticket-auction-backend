package com.sparta.ticketauction.domain.seat.entity;

import org.hibernate.annotations.Comment;

import com.sparta.ticketauction.domain.place.entity.Place;
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
@Table(name = "seat")
public class Seat extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("구역명")
	@Column(name = "zone")
	private String zone;

	@Comment("좌석번호")
	@Column(name = "seat_number")
	private int seatNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "place_id")
	private Place place;

	@Builder
	private Seat(String zone, int seatNumber, Place place) {
		this.zone = zone;
		this.seatNumber = seatNumber;
		this.place = place;
	}

}
