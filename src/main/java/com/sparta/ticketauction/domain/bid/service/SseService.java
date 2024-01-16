package com.sparta.ticketauction.domain.bid.service;

public interface SseService {

	/**
	 * @param channelName - 입찰중인 경매의 sse 채널명
	 * @param bidPrice - 입찰가
	 * @return 현재 연결 중인 SseEmitter size
	 *         (만약 size=0 이라면 redisMessageListener에서 해당 채널 제거 하기 위해 return)
	 */
	int notify(String channelName, String bidPrice);
}
