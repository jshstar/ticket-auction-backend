package com.sparta.ticketauction.domain.auction.service;

import java.util.List;

import com.sparta.ticketauction.domain.goods_sequence_seat.entity.GoodsSequenceSeat;

public interface AuctionService {
	/**
	 * 경매 등록
	 * 경매 테이블에 경매정보 등록
	 * 경매 시작 - 회차별 좌석 created At
	 * 경매 마감 - 회차 공연시작 3일 전
	 * @param sequenceSeats - 경매 좌석 리스트
	 */
	void createAuction(List<GoodsSequenceSeat> sequenceSeats);

	/**
	 * 경매 종료
	 * 경매 상태 변경
	 * 예매 서비스 - 예매 호출
	 *
	 * @param auctionId - 경매 id
	 */
	void endAuction(Long auctionId);
}
