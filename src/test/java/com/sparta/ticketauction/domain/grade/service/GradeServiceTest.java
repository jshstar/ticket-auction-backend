package com.sparta.ticketauction.domain.grade.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

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
import com.sparta.ticketauction.domain.grade.entity.Grade;
import com.sparta.ticketauction.domain.grade.repository.GradeRepository;
import com.sparta.ticketauction.domain.grade.response.GradeGetResponse;

@ExtendWith(MockitoExtension.class)
public class GradeServiceTest {

	@InjectMocks
	GradeServiceImpl gradeService;

	@Mock
	GradeRepository gradeRepository;

	@Test
	void 등급_전체_조회_테스트() {
		Goods goods = Mockito.mock();
		ReflectionTestUtils.setField(goods, "id", 1L);

		List<Grade> gradeList = new ArrayList<>(
			List.of(
				Grade
					.builder()
					.name("VIP")
					.normalPrice(70000L)
					.auctionPrice(50000L)
					.goods(goods)
					.build(),
				Grade
					.builder()
					.name("일반석")
					.normalPrice(50000L)
					.auctionPrice(30000L)
					.goods(goods)
					.build()
			)
		);
		ReflectionTestUtils.setField(gradeList.get(0), "id", 1L);
		ReflectionTestUtils.setField(gradeList.get(1), "id", 2L);

		// when
		given(gradeRepository.findAllByGoodsId(anyLong())).willReturn(gradeList);
		List<GradeGetResponse> gradeGetResponseList = gradeService.getAllGrade(anyLong());

		// then
		assertEquals(gradeGetResponseList.get(0).getName(), gradeList.get(0).getName());
		assertEquals(gradeGetResponseList.get(1).getName(), gradeList.get(1).getName());
		assertEquals(gradeGetResponseList.get(0).getGradeId(), gradeList.get(0).getId());
		assertEquals(gradeGetResponseList.get(1).getGradeId(), gradeList.get(1).getId());
		assertEquals(gradeGetResponseList.get(0).getNormalPrice(), gradeList.get(0).getNormalPrice());
		assertEquals(gradeGetResponseList.get(1).getNormalPrice(), gradeList.get(1).getNormalPrice());
		assertEquals(gradeGetResponseList.get(0).getAuctionPrice(), gradeList.get(0).getAuctionPrice());
		assertEquals(gradeGetResponseList.get(1).getAuctionPrice(), gradeList.get(1).getAuctionPrice());
	}

}
