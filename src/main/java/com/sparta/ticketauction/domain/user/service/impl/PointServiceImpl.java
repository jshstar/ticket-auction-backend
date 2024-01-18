package com.sparta.ticketauction.domain.user.service.impl;

import static com.sparta.ticketauction.global.exception.ErrorCode.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.ticketauction.domain.user.entity.Point;
import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.enums.PointType;
import com.sparta.ticketauction.domain.user.repository.PointRepository;
import com.sparta.ticketauction.domain.user.response.PointChargeResponse;
import com.sparta.ticketauction.domain.user.response.PointResponse;
import com.sparta.ticketauction.domain.user.service.PointService;
import com.sparta.ticketauction.global.exception.ApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointServiceImpl implements PointService {

	private final PointRepository pointRepository;

	@Override
	@Transactional
	public void usePointForBid(User user, Long point) {
		if (user.getPoint() < point) {
			throw new ApiException(NOT_ENOUGH_POINT);
		}

		Point usePoint = Point.builder()
			.changePoint(point)
			.user(user)
			.type(PointType.USE_BIDDING)
			.build();

		user.usePoint(point);
		pointRepository.save(usePoint);
	}

	@Override
	@Transactional
	public void refundPoint(User user, Long point) {
		Point refundPoint = Point.builder()
			.changePoint(point)
			.user(user)
			.type(PointType.REFUND_BIDDING)
			.build();

		user.addPoint(point);
		pointRepository.save(refundPoint);
	}

	@Override
	@Transactional
	public void usePointForGeneralPurchase(User user, Long point) {
		if (user.getPoint() < point) {
			throw new ApiException(NOT_ENOUGH_POINT);
		}
		
		Point usePoint = Point.builder()
			.changePoint(point)
			.user(user)
			.type(PointType.USE_PURCHASE)
			.build();

		user.usePoint(point);
		pointRepository.save(usePoint);
	}

	@Override
	@Transactional
	public void chargePoint(User user, Long point, String orderId) {
		Point chargePoint = Point.builder()
			.changePoint(point)
			.user(user)
			.type(PointType.CHARGE)
			.orderId(orderId)
			.build();

		user.addPoint(point);
		pointRepository.save(chargePoint);
	}

	@Override
	public Page<PointChargeResponse> getChargePointLogList(User loginUser, Pageable pageable) {
		return pointRepository.findChargePointListByPage(loginUser.getId(), pageable);
	}

	@Override
	public Page<PointResponse> getBidOrReservationPointLogList(User user, Pageable pageable) {
		return pointRepository.findBidOrReservationPointListByPage(user.getId(), pageable);
	}
}
