package com.sparta.ticketauction.domain.schedule.service;

import java.util.List;

import com.sparta.ticketauction.domain.schedule.entity.Schedule;

public interface ScheduleService {

	// 회차 리스트 저장
	void saveAllSchedule(List<Schedule> scheduleList);

	// 회차 저장
	Schedule saveSchedule(Schedule schedule);

	// 회차 탐색
	Schedule findSchedule(Long ScheduleId);
}
