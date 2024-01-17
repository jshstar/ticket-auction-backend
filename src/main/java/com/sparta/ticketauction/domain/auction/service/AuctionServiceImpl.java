package com.sparta.ticketauction.domain.auction.service;

import java.time.Duration;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.ticketauction.domain.auction.entity.Auction;
import com.sparta.ticketauction.domain.auction.repository.AuctionRepository;
import com.sparta.ticketauction.domain.auction.request.AuctionCreateRequest;
import com.sparta.ticketauction.domain.auction.response.AuctionInfoResponse;
import com.sparta.ticketauction.domain.bid.service.BidRedisService;
import com.sparta.ticketauction.domain.bid.service.BidService;
import com.sparta.ticketauction.domain.reservation.service.ReservationService;
import com.sparta.ticketauction.domain.grade.entity.ZoneGrade;
import com.sparta.ticketauction.domain.grade.repository.ZoneGradeRepository;
import com.sparta.ticketauction.domain.schedule.entity.Schedule;
import com.sparta.ticketauction.domain.schedule.repository.ScheduleRepository;
import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.global.exception.ApiException;
import com.sparta.ticketauction.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "경매 서비스")
@RequiredArgsConstructor
@Service
public class AuctionServiceImpl implements AuctionService {
	private final AuctionRepository auctionRepository;
	private final ScheduleRepository scheduleRepository;
	private final ZoneGradeRepository zoneGradeRepository;
	private final BidService bidService;
	private final BidRedisService bidRedisService;
	private final ReservationService reservationService;

	// TODO: 1/10/24  추후 회차좌석과 이벤트기반으로 의존성 분리하기
	@Override
	@Transactional
	public void createAuction(Long scheduleId, Long zoneGradeId, AuctionCreateRequest request) {
		Schedule schedule = scheduleRepository.getReferenceById(scheduleId);
		ZoneGrade zoneGrade = zoneGradeRepository.getReferenceById(zoneGradeId);

		Auction auction = request.toEntity(schedule, zoneGrade);
		auctionRepository.save(auction);
		bidRedisService.saveWithExpire(auction, genRemainSeconds(auction));
	}

	// TODO: 1/10/24  추후 예매와 이벤트기반으로 의존성 분리하기
	@Override
	@Transactional
	public void endAuction(Long auctionId) {
		Auction auction = getAuction(auctionId);
		auction.ended();

		User bidWinner = getBidWinner(auction);
		reservationService.reserve(bidWinner, auction);
	}

	@Override
	public AuctionInfoResponse getAuctionInfo(Long auctionId) {
		Auction auction = getAuction(auctionId);
		Long remainTimeMilli = bidRedisService.getRemainTimeMilli(auctionId);
		return AuctionInfoResponse.from(auction, remainTimeMilli);
	}

	@Override
	public Auction getAuction(Long auctionId) {
		return auctionRepository.findById(auctionId)
			.orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_AUCTION));
	}

	public User getBidWinner(Auction auction) {
		return bidService.getCurrentBid(auction)
			.orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_WIN_BID))
			.getUser();
	}


	private long genRemainSeconds(Auction auction) {
		Duration duration = Duration.between(auction.getStartDateTime(), auction.getEndDateTime());
		return duration.getSeconds();
	}
}
