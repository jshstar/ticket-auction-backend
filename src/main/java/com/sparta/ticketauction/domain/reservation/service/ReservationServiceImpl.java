package com.sparta.ticketauction.domain.reservation.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.ticketauction.domain.goods_sequence_seat.entity.GoodsSequenceSeat;
import com.sparta.ticketauction.domain.goods_sequence_seat.entity.GoodsSequenceSeatID;
import com.sparta.ticketauction.domain.goods_sequence_seat.repository.GoodsSequenceSeatRepository;
import com.sparta.ticketauction.domain.reservation.entity.Reservation;
import com.sparta.ticketauction.domain.reservation.repository.ReservationRepository;
import com.sparta.ticketauction.domain.reservation.request.ReservationCreateRequest;
import com.sparta.ticketauction.domain.reservation.response.ReservationDetailResponse;
import com.sparta.ticketauction.domain.reservation.response.ReservationResponse;
import com.sparta.ticketauction.domain.seat.entity.Seat;
import com.sparta.ticketauction.domain.seat.repository.SeatRepository;
import com.sparta.ticketauction.domain.sequence.entity.Sequence;
import com.sparta.ticketauction.domain.sequence.repository.SequenceRepository;
import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.repository.UserRepository;
import com.sparta.ticketauction.global.exception.ApiException;
import com.sparta.ticketauction.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

	private final ReservationRepository reservationRepository;

	private final UserRepository userRepository;

	private final GoodsSequenceSeatRepository goodsSequenceSeatRepository;

	private final SequenceRepository sequenceRepository;

	private final SeatRepository seatRepository;

	@Override
	@Transactional
	public ReservationDetailResponse reserve(
		User user,
		Long seatId,
		Long sequenceId,
		ReservationCreateRequest request
	) {
		// 좌석 예매 가능한지 확인
		GoodsSequenceSeatID goodsSequenceSeatId = new GoodsSequenceSeatID(seatId, sequenceId);
		GoodsSequenceSeat goodsSequenceSeat = goodsSequenceSeatRepository.findById(goodsSequenceSeatId)
			.orElseThrow(); // TODO: 예외 추가하기

		if (Boolean.TRUE.equals(goodsSequenceSeat.getIsSelled())) {
			throw new ApiException(ErrorCode.ALREADY_RESERVED_SEAT);
		}

		// 클라이언트에서 전송한 금액이 실제로 결제할 금액과 같은지 확인
		if (!request.getPrice().equals(goodsSequenceSeat.getPrice())) {
			throw new ApiException(ErrorCode.INVALID_SEAT_PRICE);
		}

		// 유저 지갑 확인
		if (user.getPoint() < request.getPrice()) {
			throw new ApiException(ErrorCode.NOT_ENOUGH_POINT);
		}

		// 결제
		User savedUser = userRepository.save(user);
		savedUser.usePoint(request.getPrice());

		// 공연 정보 조회
		Sequence sequence = sequenceRepository.findSequenceWithGoodsById(sequenceId)
			.orElseThrow(); // TODO: 예외 추가하기

		// 좌석 정보 조회
		Seat seat = seatRepository.findSeatWithPlaceById(seatId)
			.orElseThrow(); // TODO: 예외 추가하기

		// 예매 정보 생성
		Reservation reservation = request.toEntity(savedUser, goodsSequenceSeat, request.getPrice());
		Reservation savedReservation = reservationRepository.save(reservation);
		return ReservationDetailResponse.builder().
			reservationId(savedReservation.getId()).
			username(user.getName()).
			reservationDate(savedReservation.getCreatedAt()).
			goodsTitle(sequence.getGoods().getName()).
			sequence(sequence.getSequence()).
			zone(seat.getZone()).
			seatNumber(seat.getSeatNumber()).
			address(seat.getPlace().getAddress()).
			goodsStartDateTime(sequence.getStartDateTime()).
			thumbnailUrl(sequence.getGoods().getGoodsImage().get(0).getS3Key()) // TODO: S3 PREFIX 붙여야함
			.build();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ReservationResponse> searchReservations(User user, int page, int size) {
		// 필요 정보: 유저이름(유저), 예약번호(예약), 예약일자(예약), 공연제목(공연), 공연시작일(회차), 예약상태(공연)
		Pageable pageable = PageRequest.of(page, size);

		return reservationRepository.findReservationsByUserId(user.getId(), pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public ReservationDetailResponse searchReservation(User user, Long reservationId) {
		ReservationDetailResponse reservation = reservationRepository.findReservationById(reservationId)
			.orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_RESERVATION));

		// 요청한 유저가 예매한 유저인지 확인
		if (!reservationRepository.existsReservationByIdAndUser_Id(reservation.getReservationId(), user.getId())) {
			throw new ApiException();
		}
		return reservationRepository.findReservationById(reservationId);
	}
}
