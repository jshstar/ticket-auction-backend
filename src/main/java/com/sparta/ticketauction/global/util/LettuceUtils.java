package com.sparta.ticketauction.global.util;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LettuceUtils {

	public static final int REFRESH_TOKEN_EXPIRATION = 60 * 60 * 24 * 30;
	private final RedisTemplate<String, String> lettuceTemplate;

	public void save(String key, String value, Integer min) {
		lettuceTemplate.opsForValue().set(key, value, min, TimeUnit.SECONDS);
	}

	public String get(String key) {
		return lettuceTemplate.opsForValue().get(key);
	}

	public void delete(String key) {
		lettuceTemplate.opsForValue().getOperations().delete(key);
	}

}
