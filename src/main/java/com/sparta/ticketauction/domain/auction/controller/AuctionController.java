package com.sparta.ticketauction.domain.auction.controller;

import static com.sparta.ticketauction.global.response.SuccessCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.ticketauction.domain.auction.response.AuctionInfoResponse;
import com.sparta.ticketauction.domain.auction.service.AuctionService;
import com.sparta.ticketauction.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auctions")
@RestController
public class AuctionController {
	private final AuctionService auctionService;

	@GetMapping("/{auctionId}")
	public ResponseEntity<ApiResponse<AuctionInfoResponse>> getAuctionInfo(@PathVariable Long auctionId) {
		var response = auctionService.getAuctionInfo(auctionId);
		return ResponseEntity.status(SUCCESS_GET_AUCTION_INFO.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_GET_AUCTION_INFO.getCode(),
					SUCCESS_GET_AUCTION_INFO.getMessage(),
					response
				)
			);
	}
}
