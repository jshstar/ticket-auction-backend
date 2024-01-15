package com.sparta.ticketauction.domain.bid.service;

import static com.sparta.ticketauction.domain.bid.constant.BidConstant.*;
import static com.sparta.ticketauction.global.exception.ErrorCode.*;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sparta.ticketauction.domain.auction.entity.Auction;
import com.sparta.ticketauction.domain.auction.repository.AuctionRepository;
import com.sparta.ticketauction.domain.bid.entity.Bid;
import com.sparta.ticketauction.domain.bid.repository.BidRepository;
import com.sparta.ticketauction.domain.bid.request.BidRequest;
import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.global.annotaion.DistributedLock;
import com.sparta.ticketauction.global.exception.ApiException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BidServiceImpl implements BidService {

	private final AuctionRepository auctionRepository;
	private final BidRepository bidRepository;
	private final BidRedisService bidRedisService;

	@Override
	@DistributedLock(key = "T(com.sparta.ticketauction.domain.bid.constant.BidConstant).AUCTION_BID_KEY_PREFIX.concat(#auctionId)")
	public void bid(Long auctionId, BidRequest bidRequest, User bidder) {
		//redis에 경매 정보 확인
		if (bidRedisService.isExpired(auctionId)) {
			throw new ApiException(ENDED_AUCTION);
		}

		//입찰 검증
		long newBidPrice = bidRequest.getPrice();
		long currentBidPrice = bidRedisService.getBidPrice(auctionId);
		validateBid(currentBidPrice, newBidPrice);
		validatePoint(bidder.getPoint(), newBidPrice);

		//기존 입찰자, 새입찰자 포인트 업데이트
		Auction auction = getAuction(auctionId);
		updateBidderPoints(bidder, newBidPrice, currentBidPrice, auction);
		//새 입찰 등록
		saveBid(bidder, newBidPrice, auction);
	}

	public void updateBidderPoints(User bidder, long newBidPrice, long currentBidPrice, Auction auction) {
		Optional<Bid> currentBid = getCurrentBid(auction);
		if (currentBid.isPresent()) {
			User currentBidder = currentBid.get().getUser();
			currentBidder.chargePoint(currentBidPrice);
		}

		//새 입찰자 포인트 차감 및 경매 입찰가 갱신
		bidder.usePoint(newBidPrice);
	}

	public void saveBid(User bidder, long newBidPrice, Auction auction) {
		Bid newBid = Bid.builder()
			.price(newBidPrice)
			.user(bidder)
			.auction(auction)
			.build();

		auction.updateBidPrice(newBidPrice);
		bidRepository.save(newBid);
		bidRedisService.setBidPrice(auction.getId(), newBidPrice);
	}

	public Auction getAuction(Long auctionId) {
		return auctionRepository.findById(auctionId)
			.orElseThrow(() -> new ApiException(NOT_FOUND_AUCTION));
	}

	public Optional<Bid> getCurrentBid(Auction auction) {
		return bidRepository.findTopByAuctionOrderByIdDesc(auction);
	}

	/**
	 * 입찰 기준
	 * 1. 최근 입찰가보다 5% 이상 커야함
	 * 2. 입찰가격만큼의 포인트를 가지고 있어야함
	 */
	private static void validateBid(long currentBidPrice, long bidPrice) {
		//입찰 검증
		currentBidPrice += (long)(currentBidPrice * BID_PRICE_INCREASE_PERCENT);
		if (currentBidPrice > bidPrice) {
			throw new ApiException(BAD_REQUEST_BID);
		}
	}

	private static void validatePoint(long point, long bidPrice) {
		//포인트가 부족한 경우
		if (point < bidPrice) {
			throw new ApiException(NOT_ENOUGH_POINT);
		}
	}
}
