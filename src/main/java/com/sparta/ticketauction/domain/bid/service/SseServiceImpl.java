package com.sparta.ticketauction.domain.bid.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.sparta.ticketauction.domain.bid.repository.SseRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "SSE Message Service")
@RequiredArgsConstructor
@Service
public class SseServiceImpl implements SseService {
	private final SseRepository sseRepository;


	public int notify(String channelName, String bidPrice) {
		List<SseEmitter> sseEmitters = sseRepository.findAllByChannelName(channelName);
		if (sseEmitters != null) {
				Iterator<SseEmitter> iterator = sseEmitters.iterator();
				while (iterator.hasNext()) {
					SseEmitter sseEmitter = iterator.next();
					try {
						sseEmitter.send(SseEmitter.event().data(bidPrice));
					} catch (IOException e) {
						iterator.remove();
					}
				}
			return sseEmitters.size();
		}
		return 0;
	}
}
