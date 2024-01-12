package com.sparta.ticketauction.domain.schedule.service;

import static com.sparta.ticketauction.global.exception.ErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sparta.ticketauction.domain.schedule.entity.Schedule;
import com.sparta.ticketauction.domain.schedule.repository.ScheduleRepository;
import com.sparta.ticketauction.global.exception.ApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

	private final ScheduleRepository sequenceRepository;

	// 회차 리스트 저장
	@Override
	public void saveAllSchedule(List<Schedule> scheduleList) {
		sequenceRepository.saveAll(scheduleList);
	}

	// 회차 저장
	@Override
	public Schedule saveSchedule(Schedule schedule) {
		return sequenceRepository.save(schedule);
	}

	// 회차 탐색
	@Override
	public Schedule findSchedule(Long scheduleId) {
		return sequenceRepository.findById(scheduleId)
			.orElseThrow(() -> new ApiException(NOT_FOUND_SEQUENCE));
	}

}
