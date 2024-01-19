package com.sparta.ticketauction.domain.bid.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sparta.ticketauction.domain.auction.entity.Auction;
import com.sparta.ticketauction.domain.bid.repository.BidRedisRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BidRedisService {
	private final BidRedisRepository redisRepository;

	public void saveWithExpire(Auction auction) {
		redisRepository.saveWithExpire(auction);
	}

	public Optional<Long> getBidPrice(Long auctionId) {
		return redisRepository.getValue(auctionId);
	}

	public void setBidPrice(Long auctionId, Long bidPrice) {
		redisRepository.setValue(auctionId, bidPrice);
	}

	public boolean isExpired(Long auctionId) {
		return redisRepository.isExpired(auctionId);
	}

	public long getRemainTimeMilli(Long auctionId) {
		long remainTimeMilli = redisRepository.getRemainTIme(auctionId);
		return Math.max(remainTimeMilli, 0L);
	}
}
