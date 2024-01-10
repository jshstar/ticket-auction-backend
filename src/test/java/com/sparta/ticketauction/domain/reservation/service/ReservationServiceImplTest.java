package com.sparta.ticketauction.domain.reservation.service;

import static com.sparta.ticketauction.domain.reservation.ReservationUtil.*;
import static com.sparta.ticketauction.global.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.goods.entity.GoodsImage;
import com.sparta.ticketauction.domain.goods_sequence_seat.entity.GoodsSequenceSeat;
import com.sparta.ticketauction.domain.goods_sequence_seat.repository.GoodsSequenceSeatRepository;
import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.reservation.entity.Reservation;
import com.sparta.ticketauction.domain.reservation.repository.ReservationRepository;
import com.sparta.ticketauction.domain.reservation.request.ReservationCreateRequest;
import com.sparta.ticketauction.domain.reservation.response.ReservationDetailResponse;
import com.sparta.ticketauction.domain.seat.entity.Seat;
import com.sparta.ticketauction.domain.seat.repository.SeatRepository;
import com.sparta.ticketauction.domain.sequence.entity.Sequence;
import com.sparta.ticketauction.domain.sequence.repository.SequenceRepository;
import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.repository.UserRepository;
import com.sparta.ticketauction.domain.user.request.UserCreateRequest;
import com.sparta.ticketauction.domain.user.util.UserUtil;
import com.sparta.ticketauction.global.exception.ApiException;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

	@Mock
	ReservationRepository reservationRepository;

	@Mock
	UserRepository userRepository;

	@Mock
	GoodsSequenceSeatRepository goodsSequenceSeatRepository;

	@Mock
	SequenceRepository sequenceRepository;

	@Mock
	SeatRepository seatRepository;

	@Mock
	PasswordEncoder passwordEncoder;

	@InjectMocks
	ReservationServiceImpl sut;

	@Nested
	class 좌석_예약_검증 {

		@Test
		void 성공() throws Exception {
			// Given
			UserCreateRequest userCreateRequest = UserUtil.getUserCreateRequest();
			User user = userCreateRequest.toEntity(passwordEncoder);
			user.chargePoint(TEST_MONEY);
			Goods goods = Goods.builder()
				.name("공연 이름")
				.ageGrade(0)
				.goodsImage(new ArrayList<>())
				.build();

			GoodsImage goodsImage = GoodsImage.builder()
				.s3Key("썸네일 url")
				.type("대표")
				.build();
			goods.getGoodsImage().add(goodsImage);
			Sequence sequence = Sequence.builder()
				.sequence(1)
				.goods(goods)
				.startDateTime(LocalDateTime.now())
				.build();

			Place place = Place.builder()
				.address("공연장 주소")
				.build();
			Seat seat = Seat.builder()
				.zone(TEST_ZONE)
				.seatNumber(1)
				.place(place)
				.build();
			GoodsSequenceSeat goodsSequenceSeat = GoodsSequenceSeat.builder()
				.isSelled(Boolean.FALSE)
				.price(TEST_PRICE)
				.seat(seat)
				.sequence(sequence)
				.build();

			Reservation reservation = Reservation.builder()
				.price(TEST_PRICE)
				.build();

			given(userRepository.save(any())).willReturn(user);
			given(goodsSequenceSeatRepository.findById(any()))
				.willReturn(Optional.ofNullable(goodsSequenceSeat));
			given(sequenceRepository.findSequenceWithGoodsById(anyLong()))
				.willReturn(Optional.ofNullable(sequence));
			given(seatRepository.findSeatWithPlaceById(anyLong()))
				.willReturn(Optional.ofNullable(seat));
			given(reservationRepository.save(any()))
				.willReturn(reservation);

			// When
			ReservationDetailResponse response = sut.reserve(
				user, TEST_SEAT_ID, TEST_SEQUENCE_ID, TEST_RESERVATION_CREATE_REQUEST
			);

			// Then
			// 포인트 결제 검증
			assertThat(user.getPoint()).isEqualTo(TEST_MONEY - TEST_PRICE);
			// 예매 정보 생성 검증
			assertThat(response.getReservationId()).isEqualTo(reservation.getId());
			assertThat(response.getUsername()).isEqualTo(user.getName());
			assertThat(response.getReservationDate()).isEqualTo(reservation.getCreatedAt());
			assertThat(response.getGoodsTitle()).isEqualTo(goods.getName());
			assertThat(response.getSequence()).isEqualTo(sequence.getSequence());
			assertThat(response.getZone()).isEqualTo(seat.getZone());
			assertThat(response.getSeatNumber()).isEqualTo(seat.getSeatNumber());
			assertThat(response.getAddress()).isEqualTo(place.getAddress());
			assertThat(response.getGoodsStartDateTime()).isEqualTo(sequence.getStartDateTime());
			assertThat(response.getThumbnailUrl()).isEqualTo(goodsImage.getS3Key());
		}

		@Test
		void 실패_포인트_부족() {
			// Given
			UserCreateRequest userCreateRequest = UserUtil.getUserCreateRequest();
			User user = userCreateRequest.toEntity(passwordEncoder);
			user.chargePoint(TEST_MONEY);

			Sequence sequence = Sequence.builder()
				.sequence(1)
				.startDateTime(LocalDateTime.now())
				.build();

			Seat seat = Seat.builder()
				.zone(TEST_ZONE)
				.seatNumber(1)
				.build();

			GoodsSequenceSeat goodsSequenceSeat = GoodsSequenceSeat.builder()
				.isSelled(Boolean.FALSE)
				.price(10000000L)
				.seat(seat)
				.sequence(sequence)
				.build();

			given(goodsSequenceSeatRepository.findById(any()))
				.willReturn(Optional.ofNullable(goodsSequenceSeat));

			// When
			ApiException exception = assertThrows(
				ApiException.class,
				() -> sut.reserve(
					user,
					TEST_SEAT_ID,
					TEST_SEQUENCE_ID,
					ReservationCreateRequest.builder()
						.price(10000000L)
						.zone(TEST_ZONE)
						.build()
				)
			);

			// Then
			assertEquals(NOT_ENOUGH_POINT.getMessage(), exception.getMessage());
		}

		@Test
		void 실패_결제_금액_오류() throws Exception {
			// Given
			UserCreateRequest userCreateRequest = UserUtil.getUserCreateRequest();
			User user = userCreateRequest.toEntity(passwordEncoder);
			user.chargePoint(TEST_MONEY);

			Sequence sequence = Sequence.builder()
				.sequence(1)
				.startDateTime(LocalDateTime.now())
				.build();

			Seat seat = Seat.builder()
				.zone(TEST_ZONE)
				.seatNumber(1)
				.build();

			GoodsSequenceSeat goodsSequenceSeat = GoodsSequenceSeat.builder()
				.isSelled(Boolean.FALSE)
				.price(12345L)
				.seat(seat)
				.sequence(sequence)
				.build();

			given(goodsSequenceSeatRepository.findById(any()))
				.willReturn(Optional.ofNullable(goodsSequenceSeat));

			// When
			ApiException exception = assertThrows(
				ApiException.class,
				() -> sut.reserve(
					user,
					TEST_SEAT_ID,
					TEST_SEQUENCE_ID,
					ReservationCreateRequest.builder()
						.price(10000000L)
						.zone(TEST_ZONE)
						.build()
				)
			);

			// Then
			assertEquals(INVALID_SEAT_PRICE.getMessage(), exception.getMessage());
		}

		@Test
		void 실패_이미_예약된_자석() throws Exception {
			// Given
			UserCreateRequest userCreateRequest = UserUtil.getUserCreateRequest();
			User user = userCreateRequest.toEntity(passwordEncoder);
			user.chargePoint(TEST_MONEY);

			Sequence sequence = Sequence.builder()
				.sequence(1)
				.startDateTime(LocalDateTime.now())
				.build();

			Seat seat = Seat.builder()
				.zone(TEST_ZONE)
				.seatNumber(1)
				.build();

			GoodsSequenceSeat goodsSequenceSeat = GoodsSequenceSeat.builder()
				.isSelled(Boolean.TRUE)
				.price(TEST_PRICE)
				.seat(seat)
				.sequence(sequence)
				.build();

			given(goodsSequenceSeatRepository.findById(any()))
				.willReturn(Optional.ofNullable(goodsSequenceSeat));

			// When
			ApiException exception = assertThrows(
				ApiException.class,
				() -> sut.reserve(
					user,
					TEST_SEAT_ID,
					TEST_SEQUENCE_ID,
					ReservationCreateRequest.builder()
						.price(TEST_PRICE)
						.zone(TEST_ZONE)
						.build()
				)
			);

			// Then
			assertEquals(ALREADY_RESERVED_SEAT.getMessage(), exception.getMessage());
		}
	}
}
