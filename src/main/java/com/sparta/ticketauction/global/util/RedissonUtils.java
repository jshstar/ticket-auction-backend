package com.sparta.ticketauction.global.util;

import java.time.Duration;
import java.util.Optional;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class RedissonUtils {
	private final RedissonClient redissonClient;

	public <T> Optional<T> getValue(String key) {
		RBucket<T> bucket = redissonClient.getBucket(key);

		if (bucket.isExists()) {
			return Optional.of(bucket.get());
		}
		return Optional.empty();
	}

	public <T> void save(String key, T value, Long expSeconds) {
		RBucket<T> bucket = redissonClient.getBucket(key);
		bucket.set(value, Duration.ofSeconds(expSeconds));
	}

	public <T> void set(String key, T value) {
		RBucket<T> bucket = redissonClient.getBucket(key);
		bucket.setIfExists(value);
	}
}

