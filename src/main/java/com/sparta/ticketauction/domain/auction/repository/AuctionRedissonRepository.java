package com.sparta.ticketauction.domain.auction.repository;

import java.time.Duration;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class AuctionRedissonRepository {
	private final Codec codec = new TypedJsonJacksonCodec(Long.class);
	private final RedissonClient redissonClient;

	public void save(String key, Long value, long seconds) {
		RBucket<Long> bucket = getBucket(key);
		bucket.set(value);
		bucket.expire(Duration.ofSeconds(seconds));
	}

	public Long getValue(String key) {
		RBucket<Long> bucket = getBucket(key);
		return bucket.get();
	}

	public void setValue(String key, Long value) {
		RBucket<Long> bucket = getBucket(key);
		long remainTtl = bucket.remainTimeToLive();
		bucket.setIfExists(value, Duration.ofMillis(remainTtl));
	}

	public RBucket<Long> getBucket(String key) {
		return redissonClient.getBucket(key, codec);
	}

	public boolean existBucket(String key) {
		return getBucket(key).isExists();
	}
}
