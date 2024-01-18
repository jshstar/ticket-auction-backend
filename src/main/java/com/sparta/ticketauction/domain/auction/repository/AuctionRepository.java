package com.sparta.ticketauction.domain.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.auction.entity.Auction;

public interface AuctionRepository extends JpaRepository<Auction, Long>, AuctionRepositoryCustom {
}
