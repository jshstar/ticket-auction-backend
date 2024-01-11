package com.sparta.ticketauction.domain.bid.service;

import org.redisson.api.RBucket;
import org.springframework.stereotype.Service;

import com.sparta.ticketauction.domain.auction.entity.Auction;
import com.sparta.ticketauction.domain.bid.repository.BidRedisRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BidRedisService {
	private final BidRedisRepository redisRepository;

	public void saveWithExpire(Auction auction, long seconds) {
		redisRepository.saveWithExpire(auction, seconds);
	}

	public Long getBidPrice(Long auctionId) {
		return redisRepository.getValue(auctionId).orElse(null);
	}

	public void setBidPrice(Long auctionId, Long bidPrice) {
		redisRepository.setValue(auctionId, bidPrice);
	}

	public RBucket<Long> getBucket(Long auctionId) {
		return redisRepository.getBucket(auctionId);
	}

	public boolean isExpired(Long auctionId) {
		return redisRepository.isExpired(auctionId);
	}

}
