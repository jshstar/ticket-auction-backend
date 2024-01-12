package com.sparta.ticketauction.domain.auction.service;

import java.time.Duration;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.ticketauction.domain.auction.entity.Auction;
import com.sparta.ticketauction.domain.auction.repository.AuctionRepository;
import com.sparta.ticketauction.domain.bid.service.BidRedisService;
import com.sparta.ticketauction.domain.bid.service.BidService;
import com.sparta.ticketauction.domain.goods_sequence_seat.entity.GoodsSequenceSeat;
import com.sparta.ticketauction.domain.reservation.service.ReservationService;
import com.sparta.ticketauction.global.exception.ApiException;
import com.sparta.ticketauction.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "경매 서비스")
@RequiredArgsConstructor
@Service
public class AuctionServiceImpl implements AuctionService {
	private final AuctionRepository auctionRepository;
	private final BidService bidService;
	private final BidRedisService bidRedisService;
	private final ReservationService reservationService;

	// TODO: 1/10/24  추후 회차좌석과 이벤트기반으로 의존성 분리하기
	@Override
	@Transactional
	public void createAuction(List<GoodsSequenceSeat> sequenceSeats) {
		List<Auction> auctions = sequenceSeats.stream().map(sequenceSeat ->
				Auction.builder()
					.startPrice(sequenceSeat.getPrice())
					.startDateTime(sequenceSeat.getCreatedAt())
					.endDateTime(sequenceSeat.getSequence().getStartDateTime())
					.sequenceSeat(sequenceSeat)
					.build()
			)
			.toList();
		auctionRepository.saveAll(auctions);

		auctions.forEach(auction -> {
				log.debug("success create auction! id: {}", auction.getId());
				bidRedisService.saveWithExpire(auction, genRemainSeconds(auction));
			}
		);
	}

	// TODO: 1/10/24  추후 예매와 이벤트기반으로 의존성 분리하기
	@Override
	@Transactional
	public void endAuction(Long auctionId) {
		Auction auction = getAuction(auctionId);
		auction.ended();

		User bidWinner = getBidWinner(auction);

		log.info("예매 성공! id: {]", auctionId);
		reservationService.reserve(auction, bidWinner);
	}

	public User getBidWinner(Auction auction) {
		return bidService.getCurrentBid(auction)
			.orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_WIN_BID))
			.getUser();
	}

	public Auction getAuction(Long auctionId) {
		return auctionRepository.findById(auctionId)
			.orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_AUCTION));
	}

	private long genRemainSeconds(Auction auction) {
		Duration duration = Duration.between(auction.getStartDateTime(), auction.getEndDateTime());
		return duration.getSeconds();
	}
}
