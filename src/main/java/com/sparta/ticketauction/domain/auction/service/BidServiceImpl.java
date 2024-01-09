package com.sparta.ticketauction.domain.auction.service;

import static com.sparta.ticketauction.domain.auction.constant.AuctionConstant.*;
import static com.sparta.ticketauction.global.exception.ErrorCode.*;

import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import com.sparta.ticketauction.domain.auction.entity.Auction;
import com.sparta.ticketauction.domain.auction.entity.Bid;
import com.sparta.ticketauction.domain.auction.repository.AuctionRepository;
import com.sparta.ticketauction.domain.auction.repository.BidRepository;
import com.sparta.ticketauction.domain.auction.request.BidRequest;
import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.global.annotaion.DistributedLock;
import com.sparta.ticketauction.global.exception.ApiException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class BidServiceImpl implements BidService {

	private final AuctionRepository auctionRepository;
	private final BidRepository bidRepository;
	private final RedissonClient redissonClient;

	@Override
	@DistributedLock(key = "T(com.sparta.ticketauction.domain.auction.constant.AuctionConstant).AUCTION_BID_KEY_PREFIX.concat(#auctionId)")
	public void bid(Long auctionId, BidRequest bidRequest, User loginUser) {
		String key = AUCTION_BID_KEY_PREFIX + auctionId;
		RAtomicLong currentBid = redissonClient.getAtomicLong(key);

		if (currentBid.remainTimeToLive() < 1) {
			throw new ApiException(ENDED_AUCTION);
		}

		Auction auction = auctionRepository.getReferenceById(auctionId);
		long currentBidPrice = redissonClient.getAtomicLong(key).get();
		long increaseBidPrice = (long)(currentBidPrice * BID_PRICE_INCREASE_PERCENT);
		long bidPrice = bidRequest.getPrice();

		if (increaseBidPrice > bidPrice) {
			throw new ApiException(BAD_REQUEST_BID);
		}

		//경매 엔티티 입찰가 갱신 및 입찰 테이블 save
		auction.updateBidPrice(bidPrice);
		Bid bid = Bid.builder()
			.price(bidPrice)
			.user(loginUser)
			.auction(auction)
			.build();

		bidRepository.save(bid);
		currentBid.set(bidPrice);
	}
}
