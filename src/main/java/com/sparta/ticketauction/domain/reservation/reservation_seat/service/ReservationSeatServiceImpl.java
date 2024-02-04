package com.sparta.ticketauction.domain.reservation.reservation_seat.service;

import static com.sparta.ticketauction.domain.reservation.reservation_seat.repository.ReservationSeatQueryRepositoryImpl.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import com.sparta.ticketauction.domain.reservation.reservation_seat.entity.ReservationSeat;
import com.sparta.ticketauction.domain.reservation.reservation_seat.repository.ReservationSeatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationSeatServiceImpl implements ReservationSeatService {

	private final RedisTemplate<String, Object> redisTemplate;
	private final ReservationSeatRepository seatRepository;

	@Override
	@EventListener(ApplicationReadyEvent.class) // 서버 모든 준비가 끝나면 캐시 업로드
	public void seatCacheWarmUp() {

		// TODO: 모든 좌석을 메모리에 들고 한번에 순회하지 말고 분할 접근하도록 변경해야함
		List<ReservationSeat> seats = seatRepository.findActiveScheduleSeats();
		Map<String, List<Integer>> cache = new HashMap<>(); //
		Map<String, Set<Long>> scheduleZoneGrades = new HashMap<>();
		Map<String, LocalDateTime> timeMap = new HashMap<>();

		// 모든 좌석 순회하면서 각 좌석을 Schedule:ZoneGradeId 형식의 key에, value는 seatNumber list
		seats.forEach((seat) -> {
			Long scheduleId = seat.getId().getScheduleId();
			Long zoneGradeId = seat.getId().getZoneGradeId();
			Integer seatNumber = seat.getId().getSeatNumber();

			// 회차별 시작 시간 저장
			timeMap.put(scheduleId.toString(), seat.getSchedule().getStartDateTime());
			String szKey = "{%s%d}".formatted(SEAT_CACHE_PREFIX, scheduleId);
			// 스케쥴에 포함된 구역등급들을 저장한다
			Set<Long> zoneGrades = scheduleZoneGrades.getOrDefault(szKey, new HashSet<>());
			zoneGrades.add(zoneGradeId);
			scheduleZoneGrades.put(szKey, zoneGrades);

			String cacheKey = "{%s%d}:%d".formatted(SEAT_CACHE_PREFIX, scheduleId, zoneGradeId);
			// 캐시에 좌석번호를 저장한다
			List<Integer> seatNumbers = cache.getOrDefault(cacheKey, new ArrayList<>());
			seatNumbers.add(seatNumber);
			cache.put(cacheKey, seatNumbers);
		});

		// schedule에 포함된 zoneGradeId 목록 redis에 등록
		Set<Map.Entry<String, Set<Long>>> szEntry = scheduleZoneGrades.entrySet();
		LocalDateTime now = LocalDateTime.now();

		List<Object> szResult = redisTemplate.executePipelined((RedisConnection connection) -> {
			StringRedisSerializer keySerializer = (StringRedisSerializer)redisTemplate.getKeySerializer();
			Jackson2JsonRedisSerializer<Set<Long>> valueSerializer =
				new Jackson2JsonRedisSerializer<>((Class)Set.class);

			szEntry.forEach(entry -> {
				String scheduleId = entry.getKey().substring(
					entry.getKey().indexOf(":") + 1,
					entry.getKey().indexOf("}")
				);
				Duration between = Duration.between(now, timeMap.get(scheduleId));
				long ttl = TimeUnit.SECONDS.toSeconds(between.getSeconds()); // TTL 값 설정
				byte[] keyByte = keySerializer.serialize(entry.getKey());
				byte[] valueByte = valueSerializer.serialize(entry.getValue());
				connection.set(keyByte, valueByte);
				connection.expire(keyByte, ttl);
			});
			return null;
		});

		// 캐시 redis에 등록
		List<Object> cacheResult = redisTemplate.executePipelined((RedisConnection connection) -> {
			StringRedisSerializer keySerializer = (StringRedisSerializer)redisTemplate.getKeySerializer();
			Jackson2JsonRedisSerializer<List<Integer>> valueSerializer =
				new Jackson2JsonRedisSerializer<>((Class)List.class);

			Set<Map.Entry<String, List<Integer>>> cacheEntry = cache.entrySet();
			cacheEntry.forEach(entry -> {
				byte[] keyByte = keySerializer.serialize(entry.getKey());
				byte[] valueByte = valueSerializer.serialize(entry.getValue());
				connection.set(keyByte, valueByte);
			});

			return null;
		});
	}
}
