package com.sparta.ticketauction.domain.reservation.reservation_seat.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReservationSeatId implements Serializable {

	private Long scheduleId;

	private Long zoneGradeId;

	private Integer seatNumber;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ReservationSeatId that = (ReservationSeatId)o;
		return Objects.equals(getScheduleId(), that.getScheduleId()) && Objects.equals(getZoneGradeId(),
			that.getZoneGradeId()) && Objects.equals(getSeatNumber(), that.getSeatNumber());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getScheduleId(), getZoneGradeId(), getSeatNumber());
	}
}
