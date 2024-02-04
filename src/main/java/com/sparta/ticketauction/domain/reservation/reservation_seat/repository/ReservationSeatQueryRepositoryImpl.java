package com.sparta.ticketauction.domain.reservation.reservation_seat.repository;

import static com.sparta.ticketauction.domain.reservation.reservation_seat.entity.QReservationSeat.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.ticketauction.domain.reservation.reservation_seat.entity.ReservationSeat;
import com.sparta.ticketauction.domain.seat.response.ReservedSeatResponse;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReservationSeatQueryRepositoryImpl implements ReservationSeatQueryRepository {

	private final JPAQueryFactory query;
	private final RedisTemplate<String, Object> redisTemplate;
	public static final String SEAT_CACHE_PREFIX = "SEAT:";

	@Override
	public List<ReservationSeat> findReservationSeatsByReservationId(Long reservationId) {
		return query.select(reservationSeat)
			.from(reservationSeat)
			.where(reservationSeat.reservation.id.eq(reservationId))
			.fetch();
	}

	@Override
	public List<ReservedSeatResponse> findReservedSeats(Long scheduleId) {
		List<ReservedSeatResponse> result = query
			.select(Projections.constructor(
				ReservedSeatResponse.class,
				reservationSeat.id.zoneGradeId,
				reservationSeat.id.seatNumber
			))
			.from(reservationSeat)
			.where(reservationSeat.id.scheduleId.eq(scheduleId))
			.fetch();
		return result;
	}

	@Override
	public List<ReservedSeatResponse> findReservedSeatsFromCache(Long scheduleId) {
		List<ReservedSeatResponse> seats = new ArrayList<>(); // 결과 저장
		// scheduleId에 포함된 zoneGradeId 목록 조회
		List<Integer> zoneGradeIdIntegers = (List<Integer>)
			redisTemplate.opsForValue().get("{%s%d}".formatted(SEAT_CACHE_PREFIX, scheduleId));
		if (zoneGradeIdIntegers == null) {
			return seats;
		}
		List<Long> zoneGradeIds = zoneGradeIdIntegers.stream()
			.map(Integer::longValue)
			.toList();

		// // {SEAT:scheduleId}:zoneGradeId 키 조합으로 파이프라인 추가
		// List<Object> results = redisTemplate.executePipelined((RedisCallback<?>)connection -> {
		// 		zoneGradeIds.forEach(zoneGradeId -> { // 파이프라인 get
		// 			StringRedisSerializer keySerializer = (StringRedisSerializer)redisTemplate.getKeySerializer();
		// 			String key = "{%s%d}:%d".formatted(SEAT_CACHE_PREFIX, scheduleId, zoneGradeId);
		// 			byte[] keyByte = keySerializer.serialize(key);
		// 			connection.get(keyByte);
		// 		});
		// 		return null;
		// 	}
		// );
		//
		// // 결과 변환해서 리턴
		// for (int i = 0; i < results.size(); i++) {
		// 	Long zoneGradeId = zoneGradeIds.get(i);
		// 	for (Integer seatNumber : (List<Integer>)results.get(i)) {
		// 		seats.add(new ReservedSeatResponse(zoneGradeId, seatNumber));
		// 	}
		// }

		// {SEAT:scheduleId}:zoneGradeId 키 조합으로 단건 get
		zoneGradeIds.forEach(zoneGradeId -> { // 단건 get
			String key = "{%s%d}:%d".formatted(SEAT_CACHE_PREFIX, scheduleId, zoneGradeId);
			((List<Integer>)redisTemplate.opsForValue().get(key)).forEach(seatNumber -> {
				seats.add(new ReservedSeatResponse(zoneGradeId, seatNumber));
			});
		});
		return seats;
	}
}
