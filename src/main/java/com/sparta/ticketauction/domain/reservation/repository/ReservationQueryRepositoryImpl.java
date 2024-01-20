package com.sparta.ticketauction.domain.reservation.repository;

import static com.sparta.ticketauction.domain.goods.entity.QGoods.*;
import static com.sparta.ticketauction.domain.place.entity.QPlace.*;
import static com.sparta.ticketauction.domain.reservation.entity.QReservation.*;
import static com.sparta.ticketauction.domain.reservation.reservation_seat.entity.QReservationSeat.*;
import static com.sparta.ticketauction.domain.schedule.entity.QSchedule.*;
import static com.sparta.ticketauction.domain.user.entity.QUser.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.reservation.dto.ReservationSeatInfo;
import com.sparta.ticketauction.domain.reservation.entity.Reservation;
import com.sparta.ticketauction.domain.reservation.reservation_seat.entity.ReservationSeat;
import com.sparta.ticketauction.domain.reservation.response.ReservationDetailResponse;
import com.sparta.ticketauction.domain.reservation.response.ReservationResponse;
import com.sparta.ticketauction.domain.schedule.entity.Schedule;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ReservationQueryRepositoryImpl implements ReservationQueryRepository {

	private final JPAQueryFactory query;

	@Override
	public Long countReservationSeats(Long userId, Long scheduleId) {
		return query.select(reservationSeat.count()).from(reservationSeat)
			.join(reservationSeat.schedule, schedule)
			.join(reservationSeat.reservation, reservation)
			.where(reservation.user.id.eq(userId), schedule.id.eq(scheduleId))
			.fetchOne();
	}

	@Override
	public Optional<ReservationDetailResponse> getReservationDetailResponse(Long reservationId) {
		// 예약 번호(예약), 예매자(유저) / 제목(공연), useDate(회차) address(공연장), seats(구역)
		// TODO: 필요한 데이터만 가져오도록 최적화 해야함
		Reservation reservation1 = query.select(reservation)
			.from(reservation)
			.innerJoin(reservation.user, user).fetchJoin()
			.where(reservation.id.eq(reservationId))
			.fetchOne();

		List<ReservationSeat> reservationSeats = query.select(reservationSeat)
			.from(reservationSeat)
			.innerJoin(reservationSeat.schedule, schedule).fetchJoin()
			.innerJoin(schedule.goods, goods).fetchJoin()
			.innerJoin(goods.place, place).fetchJoin()
			.where(reservationSeat.reservation.id.eq(reservation1.getId()))
			.fetch();
		List<ReservationSeatInfo> seatInfos = new ArrayList<>();
		reservationSeats.forEach(
			// TODO: n+1 문제 최적화 해야함
			seat -> {
				seatInfos.add(new ReservationSeatInfo(
					seat.getZoneGrade().getZone().getName(),
					seat.getId().getSeatNumber())
				);
			}
		);

		Schedule schedule1 = reservationSeats.get(0).getSchedule();
		Place place1 = reservationSeats.get(0).getSchedule().getGoods().getPlace();

		ReservationDetailResponse response = ReservationDetailResponse.builder()
			.username(reservation1.getUser().getName())
			.title(schedule1.getGoods().getTitle())
			.seats(seatInfos)
			.useDate(schedule1.getStartDateTime())
			.reservationId(reservation1.getId())
			.address(place1.getAddress())
			.build();

		return Optional.ofNullable(response);
	}

	@Override
	public Page<ReservationResponse> getReservationsResponse(Long userId, Pageable pageable) {
		// 예약번호(예약), 예매일(예약), 상태(예약), 매수(예약좌석) / 제목(공연), 이용일(회차),  취소가능일(회차),
		// TODO: 필요한 데이터만 가져오도록 최적화 해야함
		List<Reservation> reservations = query.select(reservation)
			.from(reservation)
			.innerJoin(reservation.user, user)
			.where(reservation.user.id.eq(userId))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Reservation> countQuery = query.selectFrom(reservation)
			.innerJoin(reservation.user, user)
			.where(reservation.user.id.eq(userId));

		List<ReservationResponse> content = reservations.stream().map(reservation1 -> {
			// TODO: n+1 문제 발생하는거 최적화 해야함
			return ReservationResponse.builder()
				.title(reservation1.getSchedule().getGoods().getTitle())
				.reservationId(reservation1.getId())
				.reservationDate(reservation1.getCreatedAt())
				.status(reservation1.getStatus())
				.numberOfTicket(reservation1.getReservationAmount())
				.useDate(reservation1.getSchedule().getStartDateTime())
				.cancelDeadline(reservation1.getSchedule().getStartDateTime())
				.build();
		}).toList();

		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
	}

}
