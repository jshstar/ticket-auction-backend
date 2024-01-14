package com.sparta.ticketauction.domain.reservation.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Comment;

import com.sparta.ticketauction.domain.reservation_seat.entity.ReservationSeat;
import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.global.entity.BaseEntity;

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

	@OneToMany(mappedBy = "reservation")
	private List<ReservationSeat> reservationSeats = new ArrayList<>();

	@Builder
	private Reservation(User user, Long price, ReservationStatus status) {
		this.user = user;
		this.price = price;
		this.status = status;
	}

	public void addSeat(ReservationSeat seat) {
		seat.setReservation(this);
		reservationSeats.add(seat);
	}
}
