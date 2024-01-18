package com.sparta.ticketauction.domain.bid.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.auction.entity.Auction;
import com.sparta.ticketauction.domain.bid.entity.Bid;

public interface BidRepository extends JpaRepository<Bid, Long>, BidRepositoryCustom {
	Optional<Bid> findTopByAuctionOrderByIdDesc(Auction auction);
}
