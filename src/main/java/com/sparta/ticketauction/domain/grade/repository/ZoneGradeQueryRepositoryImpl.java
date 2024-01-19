package com.sparta.ticketauction.domain.grade.repository;

import static com.sparta.ticketauction.domain.grade.entity.QGrade.*;
import static com.sparta.ticketauction.domain.grade.entity.QZoneGrade.*;
import static com.sparta.ticketauction.domain.place.entity.QZone.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.ticketauction.domain.grade.entity.ZoneGrade;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ZoneGradeQueryRepositoryImpl implements ZoneGradeQueryRepository {

	private JPAQueryFactory query;

	@Override
	public Optional<ZoneGrade> findZoneGrade(Long id, boolean fetchZone, boolean fetchGrade) {
		JPAQuery<ZoneGrade> query = this.query.select(zoneGrade)
			.from(zoneGrade)
			.where(zoneGrade.id.eq(id));

		if (fetchGrade) {
			query.innerJoin(zoneGrade.grade, grade).fetchJoin();
		}
		if (fetchZone) {
			query.innerJoin(zoneGrade.zone, zone).fetchJoin();
		}

		return Optional.ofNullable(query.fetchOne());
	}
}
