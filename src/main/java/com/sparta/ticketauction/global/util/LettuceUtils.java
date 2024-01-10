package com.sparta.ticketauction.global.util;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LettuceUtils {

	private final RedisTemplate<String, String> lettuceTemplate;

	public void save(String key, String value, long time) {
		lettuceTemplate.opsForValue().set(key, value, time, TimeUnit.MILLISECONDS);
	}

	public String get(String key) {
		return lettuceTemplate.opsForValue().get(key);
	}

	public void delete(String key) {
		lettuceTemplate.opsForValue().getOperations().delete(key);
	}

}
