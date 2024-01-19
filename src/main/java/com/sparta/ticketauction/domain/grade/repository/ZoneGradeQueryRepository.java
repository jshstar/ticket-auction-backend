package com.sparta.ticketauction.domain.grade.repository;

import java.util.Optional;

import com.sparta.ticketauction.domain.grade.entity.ZoneGrade;

public interface ZoneGradeQueryRepository {

	/**
	 * ZoneGrade를 가져올 때, 조건에 따라 zone과 grade를 fetch join한다.
	 * @param id
	 * @param fetchZone
	 * @param fetchGrade
	 * @return ZoneGrade
	 */
	Optional<ZoneGrade> findZoneGrade(Long id, boolean fetchZone, boolean fetchGrade);
}
