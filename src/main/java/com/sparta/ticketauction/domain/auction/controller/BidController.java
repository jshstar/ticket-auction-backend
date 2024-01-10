package com.sparta.ticketauction.domain.auction.controller;

import static com.sparta.ticketauction.global.response.SuccessCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.ticketauction.domain.auction.request.BidRequest;
import com.sparta.ticketauction.domain.auction.service.BidService;
import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.global.annotaion.CurrentUser;
import com.sparta.ticketauction.global.dto.EmptyObject;
import com.sparta.ticketauction.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auctions/{auctionId}/bids")
@RestController
public class BidController {
	private final BidService bidService;

	/*입찰하기*/
	@PostMapping
	public ResponseEntity<ApiResponse<EmptyObject>> bid(
		@PathVariable Long auctionId,
		@Valid @RequestBody BidRequest bidRequest,
		@CurrentUser User loginUser
	) {
		bidService.bid(auctionId, bidRequest, loginUser);
		return ResponseEntity.status(SUCCESS_BID.getHttpStatus())
			.body(ApiResponse.of(SUCCESS_BID.getCode(), SUCCESS_BID.getMessage()));
	}
}
