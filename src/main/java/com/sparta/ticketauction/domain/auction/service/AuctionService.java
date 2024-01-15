package com.sparta.ticketauction.domain.auction.service;

import com.sparta.ticketauction.domain.auction.request.AuctionCreateRequest;

public interface AuctionService {
	/**
	 * 경매 등록
	 * 경매 테이블에 경매정보 등록
	 * 경매 시작 - 회차별 좌석 created At
	 * 경매 마감 - 회차 공연시작 3일 전
	 *
	 * @param scheduleId - 공연 회차 식별자 ID
	 * @param zoneGradeId -  구역등급 식별자 ID
	 */
	void createAuction(Long scheduleId, Long zoneGradeId, AuctionCreateRequest createRequest);

	/**
	 * 경매 종료
	 * 경매 상태 변경
	 * 예매 서비스 - 예매 호출
	 *
	 * @param auctionId - 경매 id
	 */
	void endAuction(Long auctionId);
}
