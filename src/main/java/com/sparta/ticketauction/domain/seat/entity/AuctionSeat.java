package com.sparta.ticketauction.domain.seat.entity;

import org.hibernate.annotations.Comment;

import com.sparta.ticketauction.domain.schedule.entity.Schedule;
import com.sparta.ticketauction.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "auction_seat")
public class AuctionSeat extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("좌석번호")
	@Column(name = "seat_number", nullable = false)
	private Integer seatNumber;

	@Comment("회차")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sequence_id", nullable = false)
	private Schedule schedule;

	@Comment("구역등급")
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "zone_grade_id", nullable = false)
	private ZoneGrade zoneGrade;

	@Builder
	public AuctionSeat(Integer seatNumber, Schedule schedule, ZoneGrade zoneGrade) {
		this.seatNumber = seatNumber;
		this.schedule = schedule;
		this.zoneGrade = zoneGrade;
	}
}
