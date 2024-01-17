package com.sparta.ticketauction.domain.bid.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sparta.ticketauction.domain.bid.response.BidInfoResponse;
import com.sparta.ticketauction.domain.user.entity.User;

public interface BidRepositoryCustom {
	Page<BidInfoResponse> getMyBids(Long auctionId, User user, Pageable pageable);
}
