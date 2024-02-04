package com.sparta.ticketauction.domain.reservation.service;

import static com.sparta.ticketauction.domain.reservation.reservation_seat.repository.ReservationSeatQueryRepositoryImpl.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.sparta.ticketauction.domain.auction.entity.Auction;
import com.sparta.ticketauction.domain.auction.repository.AuctionRepository;
import com.sparta.ticketauction.domain.bid.entity.Bid;
import com.sparta.ticketauction.domain.grade.entity.ZoneGrade;
import com.sparta.ticketauction.domain.grade.service.ZoneGradeService;
import com.sparta.ticketauction.domain.reservation.dto.ReservationSeatInfo;
import com.sparta.ticketauction.domain.reservation.entity.Reservation;
import com.sparta.ticketauction.domain.reservation.entity.ReservationStatus;
import com.sparta.ticketauction.domain.reservation.repository.ReservationRepository;
import com.sparta.ticketauction.domain.reservation.request.ReservationCreateRequest;
import com.sparta.ticketauction.domain.reservation.reservation_seat.entity.ReservationSeat;
import com.sparta.ticketauction.domain.reservation.reservation_seat.repository.ReservationSeatRepository;
import com.sparta.ticketauction.domain.reservation.reservation_seat.request.ReservationSeatCreateRequest;
import com.sparta.ticketauction.domain.reservation.response.ReservationDetailResponse;
import com.sparta.ticketauction.domain.reservation.response.ReservationResponse;
import com.sparta.ticketauction.domain.schedule.entity.Schedule;
import com.sparta.ticketauction.domain.schedule.service.ScheduleService;
import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.service.PointService;
import com.sparta.ticketauction.domain.user.service.UserService;
import com.sparta.ticketauction.global.exception.ApiException;
import com.sparta.ticketauction.global.exception.ErrorCode;
import com.sparta.ticketauction.global.util.LettuceUtils;
import com.sparta.ticketauction.global.util.UrlUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

	private final ReservationRepository reservationRepository;

	private final ScheduleService scheduleService;

	private final UserService userService;

	private final PointService pointService;

	private final ZoneGradeService zoneGradeService;

	private final ReservationSeatRepository reservationSeatRepository;

	private final LettuceUtils lettuceUtils;

	private final RedisTemplate<String, Object> redisTemplate;

	private final UrlUtil urlUtil;

	private final Integer MAX_RESERVATION_LIMIT = 2;

	private final String RESERVATION_AUTH_PREFIX = "ReservationAuth: ";

	private final AuctionRepository auctionRepository;

	@Override
	@Transactional
	public ReservationDetailResponse reserve(User user, ReservationCreateRequest request) {
		User savedUser = userService.findByUserId(user.getId());
		ReservationSeatCreateRequest seatRequest = request.getReservationSeats().get(0);
		List<ReservationSeatCreateRequest> seatCreateRequests = request.getReservationSeats();

		// 이미 예매된 좌석이 있는지 검사
		checkReservationStatus(request.getReservationSeats());

		// 최대 예매 제한 검사
		checkMaxReservationLimit(
			user,
			seatRequest.getScheduleId(),
			(long)request.getReservationSeats().size()
		);

		// 공연 시작 시간 검사
		checkReservationBeforeStart(seatRequest.getScheduleId());

		// 좌석 생성 값 검증
		checkReservationSeats(request.getReservationSeats(), request.getPrice());

		Schedule schedule = scheduleService.findScheduleWithGoodsPlace(
			seatRequest.getScheduleId(),
			true,
			true
		);

		Reservation reservation = createReservation(
			savedUser,
			request.getPrice(),
			request.getReservationSeats().size(),
			schedule
		);
		seatCreateRequests.forEach(
			seatCreateRequest -> addReservationSeat(reservation, seatCreateRequest)
		);

		// 예매, 예매 좌석 Entity 생성
		Reservation savedReservation = reservationRepository.saveAndFlush(reservation);

		// 포인트 사용
		pointService.usePoint(savedUser, reservation.getPrice());

		// 예약 정보 생성에 필요한 값 조회
		List<ReservationSeatInfo> seatInfos = reservation.getReservationSeats().stream().map(seat -> {
			ZoneGrade zoneGrade = zoneGradeService.findZoneGradeWithFetch(
				seat.getZoneGrade().getId(),
				true,
				false
			);
			return new ReservationSeatInfo(zoneGrade.getZone().getName(), seat.getId().getSeatNumber());
		}).toList();

		// redis에 조회 캐시 추가
		savedReservation.getReservationSeats().forEach(seat -> {
			addReservationSeatInfoToRedis(
				seat.getId().getScheduleId(),
				seat.getId().getZoneGradeId(),
				seat.getId().getSeatNumber(),
				seat.getSchedule().getStartDateTime()
			);
		});

		return ReservationDetailResponse.builder()
			.reservationId(savedReservation.getId())
			.address(schedule.getGoods().getPlace().getName() + " / " + schedule.getGoods().getPlace().getAddress())
			.seats(seatInfos)
			.price(reservation.getPrice())
			.title(schedule.getGoods().getTitle())
			.useDate(schedule.getStartDateTime())
			.username(savedUser.getName())
			.build();
	}

	@Override
	@Transactional
	public ReservationDetailResponse reserve(Bid bid, Auction auction) {
		User savedUser = userService.findByUserId(bid.getUser().getId());
		Long scheduleId = auction.getSchedule().getId();
		Schedule schedule = scheduleService.findScheduleWithGoodsPlace(
			scheduleId,
			true,
			true
		);

		Reservation reservation = createReservation(savedUser, bid.getPrice(), 1, schedule);
		ReservationSeatCreateRequest seatCreateRequest = ReservationSeatCreateRequest.builder()
			.scheduleId(scheduleId)
			.zoneGradeId(auction.getZoneGrade().getId())
			.seatNumber(auction.getSeatNumber())
			.build();
		addReservationSeat(reservation, seatCreateRequest);

		// 예매, 예매 좌석 Entity 생성
		Reservation savedReservation;
		try {
			savedReservation = reservationRepository.saveAndFlush(reservation);
		} catch (Exception e) {
			throw new ApiException(ErrorCode.ALREADY_RESERVED_SEAT);
		}
		// 포인트 사용
		pointService.usePoint(savedUser, reservation.getPrice());

		// redis에 조회 캐시 추가
		savedReservation.getReservationSeats().forEach(seat -> {
			addReservationSeatInfoToRedis(
				seat.getId().getScheduleId(),
				seat.getId().getZoneGradeId(),
				seat.getId().getSeatNumber(),
				seat.getSchedule().getStartDateTime()
			);
		});

		return ReservationDetailResponse.builder()
			.reservationId(savedReservation.getId())
			.address(schedule.getGoods().getPlace().getName() + " / " + schedule.getGoods().getPlace().getAddress())
			.seats(
				List.of(
					new ReservationSeatInfo(
						auction.getZoneGrade().getZone().getName(),
						auction.getSeatNumber())
				)
			)
			.price(savedReservation.getPrice())
			.title(schedule.getGoods().getTitle())
			.useDate(schedule.getStartDateTime())
			.username(savedUser.getName())
			.build();
	}

	@Override
	public ReservationDetailResponse getReservationDetailResponse(User user, Long reservationId) {
		checkReservationOwner(user, reservationId);

		return reservationRepository.getReservationDetailResponse(reservationId)
			.orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_RESERVATION));
	}

	@Override
	public Page<ReservationResponse> searchReservations(User user, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		return reservationRepository.getReservationsResponse(user.getId(), pageable);
	}

	@Override
	@Transactional
	public void cancelReservation(User user, Long reservationId) {
		User savedUser = userService.findByUserId(user.getId());
		// 유저 취소 권한 확인
		checkReservationOwner(savedUser, reservationId);

		// 포인트 환불
		Reservation reservation = reservationRepository.getById(reservationId);
		pointService.refundPoint(savedUser, reservation.getPrice());

		// 예매 취소(예매 seat 삭제, 예매 상태 변경, redis 캐시 삭제)
		List<ReservationSeat> seats = reservationSeatRepository.findReservationSeatsByReservationId(reservationId);
		seats.forEach(seat -> {
			deleteReservationSeatInfoToRedis(
				seat.getId().getScheduleId(),
				seat.getId().getZoneGradeId(),
				seat.getId().getSeatNumber(),
				reservation.getSchedule().getStartDateTime()
			);
		});
		reservation.updateStatus(ReservationStatus.CANCEL);
		reservation.deleteSeats(seats);
	}

	@Override
	public String createQRCode(User user, Long reservationId, HttpServletRequest request) {

		// 유저 생성 권한 확인
		checkReservationOwner(user, reservationId);

		// qr코드 생성(제한시간 1분)
		Random random = new Random();
		String authCode = String.valueOf(random.nextInt(899999) + 100000);
		QRCodeWriter qrCodeWriter = new QRCodeWriter();

		// 인증 url 조합
		StringBuilder sb = new StringBuilder(urlUtil.getCurrentServerUrl(request));
		sb.append("/");
		sb.append(reservationId);
		sb.append("/auth");
		sb.append("?authcode=");
		sb.append(authCode);

		try {
			BitMatrix bitMatrix = qrCodeWriter.encode(
				sb.toString(),
				BarcodeFormat.QR_CODE,
				300,
				300
			);

			BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			ImageIO.write(qrImage, "png", baos);
			byte[] imageBytes = baos.toByteArray();
			lettuceUtils.save(RESERVATION_AUTH_PREFIX + reservationId.toString(), authCode, 1000 * 60);

			return Base64.getEncoder().encodeToString(imageBytes);
		} catch (IOException | WriterException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void authenticateReservation(Long reservationId, String authCode) {
		// 인증 확인
		String code = lettuceUtils.get(RESERVATION_AUTH_PREFIX + reservationId.toString());
		if (code == null || !code.equals(authCode)) {
			throw new ApiException(ErrorCode.RESERVATION_AUTHENTICATE_FAIL);
		}
	}

	/**
	 * 이미 예매된 좌석이 있는지 확인한다.
	 * @param seatCreateRequests 예매할 좌석 목록
	 */
	private void checkReservationStatus(List<ReservationSeatCreateRequest> seatCreateRequests) {
		seatCreateRequests.forEach(request -> {
			if (reservationSeatRepository.existsById(request.toId())) {
				throw new ApiException(ErrorCode.ALREADY_RESERVED_SEAT);
			}
			if (isAuctionSeat(request.getScheduleId(), request.getZoneGradeId(), request.getSeatNumber())) {
				throw new ApiException(ErrorCode.ALREADY_RESERVED_SEAT);
			}
		});
	}

	/**
	 * 예매 엔티티를 생성한다.
	 * @param user 예매 유저
	 * @param price 예매 가격
	 */
	private Reservation createReservation(User user, Long price, Integer reservationAmount, Schedule schedule) {
		return Reservation.builder()
			.user(user)
			.price(price)
			.status(ReservationStatus.OK)
			.reservationAmount(reservationAmount)
			.schedule(schedule)
			.build();
	}

	/**
	 * 최대 좌석 예매 제한 검사
	 * @param user 예매 유저
	 * @param scheduleId 예매할 회차 id
	 * @param reservationSize 예매할 크기
	 */
	private void checkMaxReservationLimit(User user, Long scheduleId, Long reservationSize) {
		Long countReservations = reservationRepository.countReservationSeats(user.getId(), scheduleId);

		if (countReservations + reservationSize > MAX_RESERVATION_LIMIT) {
			throw new ApiException(ErrorCode.MAX_RESERVATION_LIMIT);
		}
	}

	/**
	 * 공연 시작 전인지 검사
	 * @param scheduleId 검사할 공연 회차
	 */
	private void checkReservationBeforeStart(Long scheduleId) {
		Schedule schedule = scheduleService.findSchedule(scheduleId);

		LocalDateTime startDateTime = schedule.getStartDateTime();
		if (LocalDateTime.now().isAfter(startDateTime)) {
			throw new ApiException(ErrorCode.ALREADY_START_SCHEDULE);
		}
	}

	private void addReservationSeat(Reservation reservation, ReservationSeatCreateRequest request) {
		Schedule schedule = scheduleService.findSchedule(request.getScheduleId());
		ZoneGrade zoneGrade = zoneGradeService
			.findZoneGradeWithFetch(request.getZoneGradeId(), true, true);

		reservation.addSeat(
			ReservationSeat.builder()
				.id(request.toId())
				.schedule(schedule)
				.zoneGrade(zoneGrade)
				.build()
		);
	}

	/**
	 * 좌석 생성 번호가 올바른지 검사한다.
	 * 프론트가 보낸 가격과 실제 좌석 가격이 동일한지 검사한다.
	 * @param requests 좌석 정보
	 * @param clientPrice 프론트가 보낸 가격
	 */
	private void checkReservationSeats(List<ReservationSeatCreateRequest> requests, Long clientPrice) {
		// TODO: 성능 개선하기 - ZoneGradeId 리스트로 한번에 조회하면 쿼리 호출을 줄일 수 있을 것으로 예상
		Long totalPrice = requests.stream().map(request -> {
			ZoneGrade zoneGrade = zoneGradeService.findZoneGradeWithFetch(
				request.getZoneGradeId(),
				true,
				true
			);
			if (request.getSeatNumber() > zoneGrade.getZone().getSeatNumber()) {
				throw new ApiException(ErrorCode.INVALID_SEAT_NUMBER);
			}
			return zoneGrade.getGrade().getNormalPrice();
		}).reduce(0L, Long::sum);

		if (!totalPrice.equals(clientPrice)) {
			throw new ApiException(ErrorCode.INVALID_SEAT_PRICE);
		}
	}

	/**
	 * 요청 유저와 예매한 유저가 동일한지 검사한다.
	 * @param user 요청 유저
	 * @param reservationId 예매 id
	 * @throws ApiException
	 */
	private void checkReservationOwner(User user, Long reservationId) {
		Reservation reservation = reservationRepository.getById(reservationId);
		if (!reservation.getUser().getId().equals(user.getId())) {
			throw new ApiException(ErrorCode.ACCESS_DENIED);
		}
	}

	/**
	 * redis에 좌석 상태 조회용 데이터를 추가한다.
	 * @param scheduleId 회차 id
	 * @param zoneGradeId 구역등급 id
	 * @param seatNumber 좌석 번호
	 * @param goodsStartDateTime 공연 시작 시간
	 */
	private void addReservationSeatInfoToRedis(
		Long scheduleId,
		Long zoneGradeId,
		Integer seatNumber,
		LocalDateTime goodsStartDateTime
	) {
		// 키 조합
		String key = "{%s%d}:%d".formatted(SEAT_CACHE_PREFIX, scheduleId, zoneGradeId);

		List<Integer> seatNumbers = (List<Integer>)redisTemplate.opsForValue().get(key);
		if (seatNumbers == null) {
			seatNumbers = new ArrayList<>();
		}
		seatNumbers.add(seatNumber);

		// 공연 시작 시간 지났으면 예외처리
		LocalDateTime now = LocalDateTime.now();
		if (now.isAfter(goodsStartDateTime)) {
			throw new ApiException(ErrorCode.ALREADY_START_SCHEDULE);
		}
		Duration between = Duration.between(now, goodsStartDateTime);
		redisTemplate.opsForValue().set(key, seatNumbers, between.getSeconds(), TimeUnit.SECONDS);
	}

	private void deleteReservationSeatInfoToRedis(
		Long scheduleId,
		Long zoneGradeId,
		Integer seatNumber,
		LocalDateTime goodsStartDateTime
	) {
		// 키 조합
		String key = "{%s%d}:%d".formatted(SEAT_CACHE_PREFIX, scheduleId, zoneGradeId);

		List<Integer> seatNumbers = (List<Integer>)redisTemplate.opsForValue().get(key);
		if (seatNumbers == null) {
			throw new ApiException(ErrorCode.NOT_FOUND_SCHEDULE);
		}
		seatNumbers.remove(seatNumber);
		LocalDateTime now = LocalDateTime.now();
		Duration between = Duration.between(now, goodsStartDateTime);
		redisTemplate.opsForValue().set(key, seatNumbers, between.getSeconds(), TimeUnit.SECONDS);
	}

	// 경매 좌석은 예매 못하도록 체크하는 메서드
	private boolean isAuctionSeat(Long scheduleId, Long zoneGradeId, Integer seatNumber) {
		Optional<Auction> seat = auctionRepository.findBySeatInfo(scheduleId, zoneGradeId, seatNumber);
		return seat.isPresent();
	}
}
