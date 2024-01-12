package com.sparta.ticketauction.domain.reservation.repository;

import static com.sparta.ticketauction.domain.goods.entity.QGoods.*;
import static com.sparta.ticketauction.domain.goods_sequence_seat.entity.QGoodsSequenceSeat.*;
import static com.sparta.ticketauction.domain.place.entity.QPlace.*;
import static com.sparta.ticketauction.domain.reservation.entity.QReservation.*;
import static com.sparta.ticketauction.domain.seat.entity.QSeat.*;
import static com.sparta.ticketauction.domain.sequence.entity.QSequence.*;
import static com.sparta.ticketauction.domain.user.entity.QUser.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.ticketauction.domain.reservation.response.ReservationDetailResponse;
import com.sparta.ticketauction.domain.reservation.response.ReservationResponse;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryCustomImpl implements ReservationRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<ReservationResponse> findReservationsByUserId(Long userId, Pageable pageable) {
		return queryFactory.select(
				Projections.constructor(
					ReservationResponse.class,
					reservation.id,
					user.name,
					reservation.createdAt,
					goods.name,
					sequence1.startDateTime,
					reservation.status
				)
			)
			.from(reservation)
			.join(reservation.user, user)
			.join(reservation.goodsSequenceSeat, goodsSequenceSeat)
			.join(goodsSequenceSeat.sequence, sequence1)
			.join(sequence1.goods, goods)
			.where(user.id.eq(userId))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(reservation.createdAt.desc()) // 성능이 중요하면 조인3개이상xxx 보통 2개까지 그럼 어떻게 쪼개? -> 연관관계를 쪼개서 나눠서 합쳐서
			.fetch();
	}

	@Override
	public Optional<ReservationDetailResponse> findReservationById(Long reservationId) {
		ReservationDetailResponse response = queryFactory.select(
				Projections.constructor(
					ReservationDetailResponse.class,
					reservation.id,
					user.name,
					reservation.createdAt,
					goods.name,
					sequence1.sequence,
					seat.zone,
					seat.seatNumber,
					place.address,
					sequence1.startDateTime
				)
			)
			.from(reservation)
			.join(reservation.user, user)
			.join(reservation.goodsSequenceSeat, goodsSequenceSeat)
			.join(goodsSequenceSeat.sequence, sequence1)
			.join(goodsSequenceSeat.seat, seat)
			.join(sequence1.goods, goods)
			.join(seat.place, place)
			.where(reservation.id.eq(reservationId))
			.fetchOne();
		return Optional.ofNullable(response);
	}
}
