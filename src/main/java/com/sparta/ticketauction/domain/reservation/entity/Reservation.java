package com.sparta.ticketauction.domain.reservation.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Comment;

import com.sparta.ticketauction.domain.reservation.reservation_seat.entity.ReservationSeat;
import com.sparta.ticketauction.domain.schedule.entity.Schedule;
import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.global.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

	@Comment("예매 좌석 목록")
	@OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReservationSeat> reservationSeats = new ArrayList<>();

	@Comment("예매 수량")
	@Column(name = "reservation_amount", nullable = false)
	private Integer reservationAmount;

	@Comment("회차")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_id")
	private Schedule schedule;

	@Builder
	private Reservation(User user, Long price, ReservationStatus status, Schedule schedule, Integer reservationAmount) {
		this.user = user;
		this.price = price;
		this.status = status;
		this.schedule = schedule;
		this.reservationAmount = reservationAmount;
	}

	public void addSeat(ReservationSeat seat) {
		seat.setReservation(this);
		reservationSeats.add(seat);
	}

	public void deleteSeats(List<ReservationSeat> seats) {
		reservationSeats.removeAll(seats);
	}

	public void updateStatus(ReservationStatus status) {
		this.status = status;
	}
}
