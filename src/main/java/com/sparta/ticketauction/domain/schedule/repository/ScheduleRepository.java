package com.sparta.ticketauction.domain.schedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.schedule.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleQueryRepository {

	// @Query("select s from Schedule s "
	// 	// 	+ "join fetch s.goods g "
	// 	// 	+ "join fetch g.goodsImage i "
	// 	// 	+ "where s.id = :sequenceId and i.type = 'POSTER_IMG'")
	// 	// Optional<Schedule> findScheduleWithGoodsById(Long scheduleId);

	// 공연Id에 해당하는 모든 회차 조회
	List<Schedule> findAllByGoodsId(Long goodsId);
}
