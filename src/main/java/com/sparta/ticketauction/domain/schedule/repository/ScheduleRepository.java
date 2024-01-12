package com.sparta.ticketauction.domain.schedule.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparta.ticketauction.domain.schedule.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

	@Query("select s from Schedule s "
		+ "join fetch s.goods g "
		+ "join fetch g.goodsImage i "
		+ "where s.id = :sequenceId and i.type = 'POSTER_IMG'")
	Optional<Schedule> findScheduleWithGoodsById(Long scheduleId);
}
