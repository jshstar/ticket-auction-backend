package com.sparta.ticketauction.domain.goods.repository;

import static com.sparta.ticketauction.domain.auction.entity.QAuction.*;
import static com.sparta.ticketauction.domain.grade.entity.QGrade.*;
import static com.sparta.ticketauction.domain.grade.entity.QZoneGrade.*;
import static com.sparta.ticketauction.domain.place.entity.QZone.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.ticketauction.domain.seat.response.AuctionSeatInfoResponse;
import com.sparta.ticketauction.domain.seat.response.SeatInfoResponse;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GoodsRepositoryCustomImpl implements GoodsRepositoryCustom {

	private final JPAQueryFactory query;

	@Override
	public List<SeatInfoResponse> findGoodsSeatInfo(Long goodsId) {
		List<SeatInfoResponse> seatInfos = query
			.select(Projections.constructor(SeatInfoResponse.class,
				zone.name,
				grade.name,
				grade.normalPrice,
				zoneGrade.id
			))
			.from(zoneGrade)
			.innerJoin(zoneGrade.grade, grade)
			.innerJoin(zoneGrade.zone, zone)
			.where(grade.goods.id.eq(goodsId))
			.fetch();

		return seatInfos;
	}

	@Override
	public List<AuctionSeatInfoResponse> findGoodsAuctionSeatInfo(Long scheduleId, Long goodsId) {
		List<AuctionSeatInfoResponse> seatInfos = query
			.select(Projections.constructor(AuctionSeatInfoResponse.class,
				zone.name,
				grade.name,
				grade.auctionPrice,
				zoneGrade.id,
				auction.id,
				auction.seatNumber,
				auction.endDateTime,
				auction.isEnded
			))
			.from(auction)
			.innerJoin(auction.zoneGrade, zoneGrade)
			.innerJoin(zoneGrade.grade, grade)
			.innerJoin(zoneGrade.zone, zone)
			.where(auction.schedule.id.eq(scheduleId))
			.where(grade.goods.id.eq(goodsId))
			.fetch();
		return seatInfos;
	}
}
