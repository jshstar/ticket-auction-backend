package com.sparta.ticketauction.domain.user.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import com.sparta.ticketauction.domain.user.entity.Point;
import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.enums.PointType;
import com.sparta.ticketauction.domain.user.response.PointChargeResponse;
import com.sparta.ticketauction.domain.user.response.PointUseResponse;
import com.sparta.ticketauction.domain.user.util.UserUtil;
import com.sparta.ticketauction.global.config.AuditingConfig;
import com.sparta.ticketauction.global.config.QueryDslConfig;

@DataJpaTest
@ActiveProfiles("test")
@Import({QueryDslConfig.class, AuditingConfig.class})
class PointRepositoryCustomImplTest {

	@Autowired
	PointRepository pointRepository;
	@Autowired
	UserRepository userRepository;

	private static Point createPoint(Long id, Long changePoint, int day) {
		PointType type = day % 2 == 0 ? PointType.CHARGE : PointType.USE;

		Point point = Point.builder()
			.changePoint(changePoint)
			.orderId(UUID.randomUUID().toString())
			.type(type)
			.user(UserUtil.getUser())
			.build();

		ReflectionTestUtils.setField(point, "id", id);
		ReflectionTestUtils.setField(point, "createdAt", LocalDateTime.now().plusDays(day));

		return point;
	}

	@BeforeEach
	void beforeEach() {
		userRepository.save(UserUtil.getUser());

		for (int i = 1; i <= 10; i++) {
			pointRepository.save(createPoint((long)i, (long)(1111 * (i + 1)), i));
		}
	}

	@Test
	void 포인트_충전_내역_조회_시_가장_최신순으로_정렬되어_조회_성공() {
		// Given
		Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "createdAt");
		User user = UserUtil.getUser();

		// When
		Page<PointChargeResponse> responses = pointRepository.findChargePointListByPage(user.getId(), pageable);

		// Then
		assertThat(responses.getContent()).hasSize(5);
		assertThat(responses.getContent().get(0).getId()).isEqualTo(10L);
		assertThat(responses.getContent().get(1).getId()).isEqualTo(8L);
		assertThat(responses.getContent().get(2).getId()).isEqualTo(6L);
		assertThat(responses.getContent().get(3).getId()).isEqualTo(4L);
		assertThat(responses.getContent().get(4).getId()).isEqualTo(2L);
	}

	@Test
	void 포인트_사용_내역_조회_시_가장_최신순으로_정렬되어_조회_성공() {
		// Given
		Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "createdAt");
		User user = UserUtil.getUser();

		// When
		Page<PointUseResponse> responses = pointRepository.findUsePointListByPage(user.getId(), pageable);

		// Then
		assertThat(responses.getContent()).hasSize(5);
		assertThat(responses.getContent().get(0).getId()).isEqualTo(9L);
		assertThat(responses.getContent().get(1).getId()).isEqualTo(7L);
		assertThat(responses.getContent().get(2).getId()).isEqualTo(5L);
		assertThat(responses.getContent().get(3).getId()).isEqualTo(3L);
		assertThat(responses.getContent().get(4).getId()).isEqualTo(1L);
	}
}