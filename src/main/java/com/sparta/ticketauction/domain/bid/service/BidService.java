package com.sparta.ticketauction.domain.bid.service;

import java.util.Optional;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.sparta.ticketauction.domain.auction.entity.Auction;
import com.sparta.ticketauction.domain.bid.entity.Bid;
import com.sparta.ticketauction.domain.bid.request.BidRequest;
import com.sparta.ticketauction.domain.user.entity.User;

public interface BidService {
	/**
	 * 입찰하기 기능
	 * 경매에 입찰하는 기능
	 *
	 * @param auctionId  - 경매 식별자 ID
	 * @param bidRequest - 입찰 요청 DTO
	 * @param loginUser - 인증된 로그인 유저
	 */
	void bid(Long auctionId, BidRequest bidRequest, User loginUser);

	/**
	 * 경매의 최근입찰 조회
	 * @param auction - 경매
	 * @return 최근 입찰
	 */
	Optional<Bid> getCurrentBid(Auction auction);

	SseEmitter subscribe(Long auctionId);
}
