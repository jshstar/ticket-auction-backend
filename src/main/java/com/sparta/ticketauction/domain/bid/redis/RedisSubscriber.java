package com.sparta.ticketauction.domain.bid.redis;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import com.sparta.ticketauction.domain.bid.service.SseService;
import com.sparta.ticketauction.global.util.LettuceUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class RedisSubscriber implements MessageListener {
	private final Map<String, String> channelLists = new ConcurrentHashMap<>();

	private final RedisMessageListenerContainer redisContainer;
	private final LettuceUtils lettuceUtils;
	private final SseService sseService;

	public void createChannel(String channelName) {
		if (!channelLists.containsKey(channelName)) {
			redisContainer.addMessageListener(this, new ChannelTopic(channelName));
			channelLists.putIfAbsent(channelName, channelName);
		}
	}

	public void removeChannel(String channelName) {
		if (channelLists.containsKey(channelName)) {
			redisContainer.removeMessageListener(this, new ChannelTopic(channelName));
			// value = null 이면 해당 키 값 제거
			channelLists.computeIfPresent(channelName,(key, value) -> channelLists.remove(key));
		}
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		String channelName = lettuceUtils.deserializeString(message.getChannel());
		String productPrice = lettuceUtils.deserializeString(message.getBody());
		int connectedSseEmittersSize  = sseService.notify(channelName, productPrice);
		if (connectedSseEmittersSize == 0) {
			removeChannel(channelName);
		}
	}
}
