package com.sparta.ticketauction.domain.schedule.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;
import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.schedule.entity.Schedule;
import com.sparta.ticketauction.domain.schedule.repository.ScheduleRepository;
import com.sparta.ticketauction.domain.schedule.response.ScheduleGetResponse;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {

	@InjectMocks
	ScheduleServiceImpl scheduleService;

	@Mock
	ScheduleRepository scheduleRepository;

	@Test
	void 회차_전체_조회_테스트() {
		// given
		GoodsInfo goodsInfo = Mockito.mock();
		Place place = Mockito.mock();
		Goods goods =
			Goods
				.builder()
				.goodsInfo(goodsInfo)
				.place(place)
				.startDate(LocalDate.of(2024, 2, 1))
				.endDate(LocalDate.of(2024, 2, 2))
				.build();

		ReflectionTestUtils.setField(goods, "id", 1L);

		List<Schedule> scheduleList = new ArrayList<>();
		scheduleList.add(
			Schedule
				.builder()
				.sequence(1)
				.startDateTime(
					LocalDateTime.of(2023, 3, 1, 15, 0, 0))
				.goods(goods)
				.build()
		);
		scheduleList.add(
			Schedule
				.builder()
				.sequence(2)
				.startDateTime(
					LocalDateTime.of(2023, 3, 2, 15, 0, 0))
				.goods(goods)
				.build()
		);
		ReflectionTestUtils.setField(scheduleList.get(0), "id", 1L);
		ReflectionTestUtils.setField(scheduleList.get(1), "id", 2L);

		// when
		given(scheduleRepository.findAllByGoodsId(anyLong())).willReturn(scheduleList);
		List<ScheduleGetResponse> scheduleGetResponses = scheduleService.getAllSchedule(1L);

		// then
		verify(scheduleRepository, times(1)).findAllByGoodsId(anyLong());
		assertEquals(scheduleGetResponses.get(0).getScheduleId(), scheduleList.get(0).getId());
		assertEquals(scheduleGetResponses.get(1).getScheduleId(), scheduleList.get(1).getId());
		assertEquals(scheduleGetResponses.get(0).getSequence(), scheduleList.get(0).getSequence());
		assertEquals(scheduleGetResponses.get(1).getSequence(), scheduleList.get(1).getSequence());
		assertEquals(scheduleGetResponses.get(0).getStartDateTime(), scheduleList.get(0).getStartDateTime());
		assertEquals(scheduleGetResponses.get(1).getStartDateTime(), scheduleList.get(1).getStartDateTime());
	}

}
