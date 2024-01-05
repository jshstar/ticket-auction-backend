package com.sparta.ticketauction.domain.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.auction.entity.Bid;

public interface BidRepository extends JpaRepository<Bid, Long> {
}
