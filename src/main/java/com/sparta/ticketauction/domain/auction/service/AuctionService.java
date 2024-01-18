package com.sparta.ticketauction.domain.auction.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sparta.ticketauction.domain.auction.request.AuctionCreateRequest;
import com.sparta.ticketauction.domain.auction.response.AuctionDetailResponse;
import com.sparta.ticketauction.domain.auction.entity.Auction;
import com.sparta.ticketauction.domain.auction.response.AuctionInfoResponse;
import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.global.exception.ApiException;

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
	 * @param auctionId - 경매 식별자 ID
	 */
	void endAuction(Long auctionId);

	/**
	 * 경매 정보 조회
	 *
	 * @param auctionId - 경매 식별자 ID
	 * @return AuctionInfoResponse - 경매 정보 응답 DTO
	 */
	AuctionDetailResponse getAuctionInfo(Long auctionId);

	/**
	 *
	 * @param auctionId - 경매 식별자 ID
	 * @return Auction - 경매 Entity
	 * @exception ApiException - 경매를 못찾는 경우
	 */
	Auction getAuction(Long auctionId);

	/**
	 * 참가한 경매 내역 조회
	 *
	 * @param loginUser - 참가 대상 유저
	 * @param pageable  - 페이징 처리 구현체
	 * @return
	 */
	Page<AuctionInfoResponse> getMyJoinedAuctions(User loginUser, Pageable pageable);
}
