package com.sparta.ticketauction.domain.auction.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sparta.ticketauction.domain.auction.entity.Auction;
import com.sparta.ticketauction.domain.auction.response.AuctionInfoResponse;
import com.sparta.ticketauction.domain.user.entity.User;

public interface AuctionRepositoryCustom {
	Page<AuctionInfoResponse> getJoinedMyAuctions(User user, Pageable pageable);

	boolean exists(Auction auction);

	Optional<Auction> findBySeatInfo(Long scheduleId, Long zoneGradeId, Integer seatNumber);
}
