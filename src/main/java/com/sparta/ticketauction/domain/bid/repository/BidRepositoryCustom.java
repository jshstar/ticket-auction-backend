package com.sparta.ticketauction.domain.bid.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sparta.ticketauction.domain.auction.entity.Auction;
import com.sparta.ticketauction.domain.bid.response.BidInfoResponse;
import com.sparta.ticketauction.domain.bid.response.BidInfoWithNickname;
import com.sparta.ticketauction.domain.user.entity.User;

public interface BidRepositoryCustom {
	Page<BidInfoResponse> getMyBids(Long auctionId, User user, Pageable pageable);

	Optional<Long> getMaxBidPrice(Auction auction);

	/**
	 * 유저가 해당 경매의 최상위 입찰자인지 검사
	 * @param userId 실행 유저 id
	 * @param auctionId 경매 id
	 * @return 최상위 입찰자라면 True, 아니면 False
	 */
	boolean isUserBidHighest(Long userId, Long auctionId);

	/**
	 * 해당 경매의 최근 입찰 내역 n 개의 유저 닉네임과 가격을 조회한다.
	 * @param auctionId 경매 id
	 * @param limit 조회할 양
	 * @return List<BidInfoWithUserResponse>
	 */
	List<BidInfoWithNickname> getLastBidsNicknameAndPrice(Long auctionId, Long limit);
}
