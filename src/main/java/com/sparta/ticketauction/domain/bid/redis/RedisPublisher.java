package com.sparta.ticketauction.domain.bid.redis;

import org.springframework.stereotype.Component;

import com.sparta.ticketauction.global.util.LettuceUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class RedisPublisher {
	private final LettuceUtils lettuceUtils;

	public void publish(String channelName, long bidPrice) {
		lettuceUtils.sendMessage(channelName, String.valueOf(bidPrice));
	}
}
