package com.sparta.ticketauction.domain.schedule.repository;

import java.util.Optional;

import com.sparta.ticketauction.domain.schedule.entity.Schedule;

public interface ScheduleQueryRepository {

	Optional<Schedule> findByIdWithGoodsInfo(Long id, boolean fetchGoods, boolean fetchPlace);
}
