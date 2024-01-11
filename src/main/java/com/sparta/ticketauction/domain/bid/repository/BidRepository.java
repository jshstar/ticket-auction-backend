package com.sparta.ticketauction.domain.bid.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.bid.entity.Bid;

public interface BidRepository extends JpaRepository<Bid, Long> {
}
