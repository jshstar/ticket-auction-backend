package com.sparta.ticketauction.domain.reservation_seat.entity;

import com.sparta.ticketauction.domain.grade.entity.ZoneGrade;
import com.sparta.ticketauction.domain.reservation.entity.Reservation;
import com.sparta.ticketauction.domain.schedule.entity.Schedule;
import com.sparta.ticketauction.global.entity.BaseEntity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "reservation_seat")
public class ReservationSeat extends BaseEntity {

	@EmbeddedId
	private ReservationSeatId id;

	@MapsId("zoneGradeId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "zone_grade_id")
	private ZoneGrade zoneGrade;

	@MapsId("scheduleId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_id")
	private Schedule schedule;

	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reservation_id")
	private Reservation reservation;

	@Builder
	public ReservationSeat(ReservationSeatId id, ZoneGrade zoneGrade, Schedule schedule) {
		this.id = id;
		this.zoneGrade = zoneGrade;
		this.schedule = schedule;
	}
}
