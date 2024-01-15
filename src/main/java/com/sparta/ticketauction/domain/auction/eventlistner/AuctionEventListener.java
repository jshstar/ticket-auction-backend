package com.sparta.ticketauction.domain.auction.eventlistner;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.sparta.ticketauction.domain.auction.service.AuctionService;
import com.sparta.ticketauction.global.event.AuctionEndEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuctionEventListener {
	private final AuctionService auctionService;

	@Async
	@EventListener
	public void listen(AuctionEndEvent auctionEndEvent) {
		auctionService.endAuction(auctionEndEvent.getId());
	}
}
