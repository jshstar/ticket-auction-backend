package com.sparta.ticketauction.domain.schedule.repository;

import static com.sparta.ticketauction.domain.goods.entity.QGoods.*;
import static com.sparta.ticketauction.domain.schedule.entity.QSchedule.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.ticketauction.domain.schedule.entity.Schedule;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ScheduleQueryRepositoryImpl implements ScheduleQueryRepository {

	private final JPAQueryFactory query;

	@Override
	public Optional<Schedule> findByIdWithGoodsInfo(Long id, boolean fetchGoods, boolean fetchPlace) {
		JPAQuery<Schedule> jpaQuery = query.select(schedule)
			.from(schedule)
			.where(schedule.id.eq(id));

		if (fetchGoods) {
			jpaQuery.innerJoin(schedule.goods, goods).fetchJoin();
		}

		if (fetchPlace) {
			jpaQuery.innerJoin(goods.place).fetchJoin();
		}

		return Optional.ofNullable(jpaQuery.fetchOne());
	}
}
