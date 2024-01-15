package com.sparta.ticketauction.domain.schedule.service;

import java.time.LocalTime;
import java.util.List;

import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.schedule.entity.Schedule;

public interface ScheduleService {

	// 회차 조회
	Schedule findSchedule(Long ScheduleId);

	//회차 생성
	void createSchedule(Goods goods, LocalTime startTime);

	//회차 요일 및 시작시간 부여
	List<Schedule> distributeSchedule(Goods goods, LocalTime startTime);
}
