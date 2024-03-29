package com.sparta.ticketauction.domain.schedule.service;

import java.time.LocalTime;
import java.util.List;

import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.schedule.entity.Schedule;
import com.sparta.ticketauction.domain.schedule.response.ScheduleGetResponse;

public interface ScheduleService {

	// 회차 조회
	Schedule findSchedule(Long ScheduleId);

	//회차 생성
	void createSchedule(Goods goods, LocalTime startTime);

	//회차 요일 및 시작시간 부여
	List<Schedule> distributeSchedule(Goods goods, LocalTime startTime);

	// 해당 공연에 대한 전 회차 조회
	List<ScheduleGetResponse> getAllSchedule(Long goodsId);

	// 회차 공연과 공연장 fetch join 선택 조회
	Schedule findScheduleWithGoodsPlace(Long scheduleId, boolean fetchGoods, boolean fetchPlace);
}
