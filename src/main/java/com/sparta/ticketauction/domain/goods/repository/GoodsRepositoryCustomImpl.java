package com.sparta.ticketauction.domain.goods.repository;

import static com.sparta.ticketauction.domain.auction.entity.QAuction.*;
import static com.sparta.ticketauction.domain.goods.entity.QGoods.*;
import static com.sparta.ticketauction.domain.goods.entity.QGoodsCategory.*;
import static com.sparta.ticketauction.domain.goods.entity.QGoodsInfo.*;
import static com.sparta.ticketauction.domain.grade.entity.QGrade.*;
import static com.sparta.ticketauction.domain.grade.entity.QZoneGrade.*;
import static com.sparta.ticketauction.domain.place.entity.QZone.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.ticketauction.domain.goods.entity.Goods;
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

	@Override
	public List<Goods> findAllByGoodsAndCategoryName(
		Long cursorId,
		int size,
		String categoryName) {
		// JPAQuery 시작
		JPAQuery<Goods> query = this.query.select(goods);
		query.from(goods)
			.leftJoin(goods.goodsInfo, goodsInfo).fetchJoin()
			.leftJoin(goodsInfo.goodsCategory, goodsCategory).fetchJoin()
			.where(goods.endDate.after(LocalDate.now()));

		// 카테고리 이름 조건
		if (categoryName != null) {
			query.where(goodsCategory.name.eq(categoryName));
		}

		// 커서 ID 기반 조건
		if (cursorId != null) {
			query.where(goods.id.gt(cursorId));
		}

		// 페이징 처리와 정렬
		return query
			.limit(size)
			.orderBy(goods.id.asc())
			.fetch();
	}
}
