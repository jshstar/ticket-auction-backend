package com.sparta.ticketauction.domain.schedule.service;

import static com.sparta.ticketauction.global.exception.ErrorCode.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.schedule.entity.Schedule;
import com.sparta.ticketauction.domain.schedule.repository.ScheduleRepository;
import com.sparta.ticketauction.domain.schedule.response.ScheduleGetResponse;
import com.sparta.ticketauction.global.exception.ApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

	private final ScheduleRepository scheduleRepository;

	// 회차 조회
	@Override
	public Schedule findSchedule(Long scheduleId) {
		return scheduleRepository.findById(scheduleId)
			.orElseThrow(() -> new ApiException(NOT_FOUND_SCHEDULE));
	}

	// 회차 생성
	@Override
	public void createSchedule(Goods goods, LocalTime localTime) {

		List<Schedule> scheduleList = distributeSchedule(goods, localTime);
		scheduleRepository.saveAll(scheduleList);
	}

	//회차 요일 및 시작시간 부여
	@Override
	public List<Schedule> distributeSchedule(Goods goods, LocalTime startTime) {
		LocalDate startDate = goods.getStartDate();
		LocalDate endDate = goods.getEndDate();

		List<Schedule> distributeSequenceList = new ArrayList<>();
		long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

		for (int i = 1; i <= daysBetween + 1; i++) {
			LocalDateTime dateTime = startDate.atTime(startTime);
			Schedule schedule =
				Schedule
					.builder()
					.startDateTime(dateTime)
					.goods(goods)
					.sequence(i)
					.build();
			distributeSequenceList.add(schedule);
			startDate = startDate.plusDays(1);
		}
		return distributeSequenceList;
	}

	// 해당 공연에 대한 전 회차 조회
	@Override
	@Transactional(readOnly = true)
	public List<ScheduleGetResponse> getAllSchedule(Long goodsId) {
		List<Schedule> scheduleList = scheduleRepository.findAllByGoodsId(goodsId);
		return scheduleList.stream().map(ScheduleGetResponse::new).toList();
	}

}
