package com.sparta.ticketauction.domain.reservation.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

	// private final ReservationRepository reservationRepository;
	//
	// private final UserRepository userRepository;
	//
	// private final GoodsSequenceSeatRepository goodsSequenceSeatRepository;
	//
	// private final SequenceRepository sequenceRepository;
	//
	// private final SeatRepository seatRepository;
	//
	// @Override
	// @Transactional
	// public ReservationDetailResponse reserve(
	// 	User user,
	// 	Long seatId,
	// 	Long sequenceId,
	// 	ReservationCreateRequest request
	// ) {
	// 	// 좌석 예매 가능한지 확인
	// 	GoodsSequenceSeatID goodsSequenceSeatId = new GoodsSequenceSeatID(seatId, sequenceId);
	// 	GoodsSequenceSeat goodsSequenceSeat = goodsSequenceSeatRepository.findById(goodsSequenceSeatId)
	// 		.orElseThrow(); // TODO: 예외 추가하기
	//
	// 	if (Boolean.TRUE.equals(goodsSequenceSeat.getIsSelled())) {
	// 		throw new ApiException(ErrorCode.ALREADY_RESERVED_SEAT);
	// 	}
	//
	// 	// 클라이언트에서 전송한 금액이 실제로 결제할 금액과 같은지 확인
	// 	if (!request.getPrice().equals(goodsSequenceSeat.getPrice())) {
	// 		throw new ApiException(ErrorCode.INVALID_SEAT_PRICE);
	// 	}
	//
	// 	// 유저 지갑 확인
	// 	if (user.getPoint() < request.getPrice()) {
	// 		throw new ApiException(ErrorCode.NOT_ENOUGH_POINT);
	// 	}
	//
	// 	// 결제
	// 	User savedUser = userRepository.save(user);
	// 	savedUser.usePoint(request.getPrice());
	//
	// 	// 공연 정보 조회
	// 	Sequence sequence = sequenceRepository.findSequenceWithGoodsById(sequenceId)
	// 		.orElseThrow(); // TODO: 예외 추가하기
	//
	// 	// 좌석 정보 조회
	// 	Seat seat = seatRepository.findSeatWithPlaceById(seatId)
	// 		.orElseThrow(); // TODO: 예외 추가하기
	//
	// 	// 예매 정보 생성
	// 	Reservation reservation = request.toEntity(savedUser, goodsSequenceSeat, request.getPrice());
	// 	Reservation savedReservation = reservationRepository.save(reservation);
	// 	return ReservationDetailResponse.from(
	// 		savedReservation.getId(),
	// 		user.getName(),
	// 		savedReservation.getCreatedAt(),
	// 		sequence.getGoodsInfo().getName(),
	// 		sequence.getSequence(),
	// 		seat.getZone(),
	// 		seat.getSeatNumber(),
	// 		seat.getPlace().getAddress(),
	// 		sequence.getStartDateTime(),
	// 		sequence.getGoodsInfo().getGoodsImage().get(0).getS3Key() // TODO: S3 PREFIX 붙여야함
	// 	);
	// }
}
