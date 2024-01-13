package com.sparta.ticketauction.domain.user.service;

import static com.sparta.ticketauction.global.exception.ErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.ticketauction.domain.user.entity.Point;
import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.enums.PointType;
import com.sparta.ticketauction.domain.user.repository.PointRepository;
import com.sparta.ticketauction.global.exception.ApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointServiceImpl implements PointService {
	
	private final PointRepository pointRepository;

	@Override
	@Transactional
	public void usePoint(User user, Long point) {
		if (user.getPoint() < point) {
			throw new ApiException(NOT_ENOUGH_POINT);
		}

		Point usePoint = Point.builder()
			.point(point)
			.user(user)
			.type(PointType.USE)
			.build();

		user.usePoint(point);
		pointRepository.save(usePoint);
	}

	@Override
	@Transactional
	public void chargePoint(User user, Long point) {
		Point chargePoint = Point.builder()
			.point(point)
			.user(user)
			.type(PointType.CHARGE)
			.build();

		user.chargePoint(point);
		pointRepository.save(chargePoint);
	}
}
