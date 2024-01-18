package com.sparta.ticketauction.domain.bid.repository;

import static com.sparta.ticketauction.domain.bid.constant.BidConstant.*;

import java.time.Duration;
import java.util.Optional;

import org.redisson.api.ExpiredObjectListener;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import com.sparta.ticketauction.domain.auction.entity.Auction;
import com.sparta.ticketauction.global.event.AuctionEndEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Repository
public class BidRedisRepository {
	private final Codec codec = new TypedJsonJacksonCodec(Long.class);
	private final RedissonClient redissonClient;
	private final ApplicationEventPublisher publisher;

	public void saveWithExpire(Auction auction) {
		RBucket<Long> bucket = getBucket(auction.getId());
		bucket.set(auction.getStartPrice(), Duration.ofSeconds(auction.genRemainSeconds()));

		bucket.addListener((ExpiredObjectListener)name -> {
			publisher.publishEvent(
				AuctionEndEvent.builder()
					.id(auction.getId())
					.build()
			);
			log.info("Auction Expired! id: {}", auction.getId());
		});
	}

	public Optional<Long> getValue(Long auctionId) {
		RBucket<Long> bucket = getBucket(auctionId);
		return Optional.ofNullable(bucket.get());
	}

	public void setValue(Long auctionId, Long bidPrice) {
		RBucket<Long> bucket = getBucket(auctionId);
		long remainTtl = bucket.remainTimeToLive();
		bucket.setIfExists(bidPrice, Duration.ofMillis(remainTtl));
	}

	public RBucket<Long> getBucket(Long auctionId) {
		return redissonClient.getBucket(AUCTION_BID_KEY_PREFIX + auctionId, codec);
	}

	public boolean isExpired(Long auctionId) {
		return getBucket(auctionId).remainTimeToLive() < 1;
	}

	public long getRemainTIme(Long auctionId) {
		RBucket<Long> bucket = getBucket(auctionId);
		return bucket.remainTimeToLive();
	}
}
