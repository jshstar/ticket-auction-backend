package com.sparta.ticketauction.domain.user.service;

import static com.sparta.ticketauction.global.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sparta.ticketauction.domain.user.entity.Point;
import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.enums.PointType;
import com.sparta.ticketauction.domain.user.repository.PointRepository;
import com.sparta.ticketauction.domain.user.service.impl.PointServiceImpl;
import com.sparta.ticketauction.domain.user.util.UserUtil;
import com.sparta.ticketauction.global.exception.ApiException;

@DisplayName("포인트 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class PointServiceImplTest {

	@InjectMocks
	PointServiceImpl sut;

	@Mock
	PointRepository pointRepository;

	@Captor
	ArgumentCaptor<Point> argumentCaptor;

	User user;

	@BeforeEach
	public void beforeEach() {
		user = UserUtil.getUser();
	}

	@Nested
	class 포인트_사용_테스트 {

		@Test
		void 보유_포인트_잔액_부족으로_실패() {
			// Given

			// When
			ApiException exception = assertThrows(
				ApiException.class,
				() -> sut.usePoint(user, 100L)
			);

			// Then
			assertThat(exception.getMessage())
				.isEqualTo(NOT_ENOUGH_POINT.getMessage());
		}

		@Test
		void 성공() {
			// Given
			user.chargePoint(100L);

			Point point = Point.builder()
				.user(user)
				.type(PointType.USE)
				.changePoint(10L)
				.build();

			given(pointRepository.save(any(Point.class))).willReturn(point);

			// When
			sut.usePoint(user, 10L);

			// Then
			verify(pointRepository).save(any(Point.class));
			verify(pointRepository).save(argumentCaptor.capture());

			assertThat(argumentCaptor.getValue().getType().getType())
				.isEqualTo(point.getType().getType());

			assertThat(argumentCaptor.getValue().getChangePoint())
				.isEqualTo(point.getChangePoint());
		}
	}

	@Nested
	class 포인트_충전_테스트 {

		@Test
		void 성공() {
			// Given

			Point point = Point.builder()
				.user(user)
				.type(PointType.CHARGE)
				.changePoint(10L)
				.build();

			given(pointRepository.save(any(Point.class))).willReturn(point);

			// When
			sut.chargePoint(user, 10L, null);

			// Then
			verify(pointRepository).save(any(Point.class));
			verify(pointRepository).save(argumentCaptor.capture());

			assertThat(argumentCaptor.getValue().getUser().getPoint())
				.isEqualTo(point.getChangePoint());
		}
	}
}