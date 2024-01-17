package com.sparta.ticketauction.domain.bid.controller;

import static com.sparta.ticketauction.domain.bid.constant.BidConstant.*;
import static com.sparta.ticketauction.global.response.SuccessCode.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.sparta.ticketauction.domain.bid.redis.RedisPublisher;
import com.sparta.ticketauction.domain.bid.request.BidRequest;
import com.sparta.ticketauction.domain.bid.response.BidInfoResponse;
import com.sparta.ticketauction.domain.bid.service.BidService;
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
	private final RedisPublisher redisPublisher;

	/*입찰하기*/
	@PostMapping
	public ResponseEntity<ApiResponse<EmptyObject>> bid(
		@PathVariable Long auctionId,
		@Valid @RequestBody BidRequest bidRequest,
		@CurrentUser User loginUser
	) {
		bidService.bid(auctionId, bidRequest, loginUser);

		//입찰 갱신 sse
		redisPublisher.publish(AUCTION_SSE_PREFIX + auctionId, bidRequest.getPrice());
		return ResponseEntity.status(SUCCESS_BID.getHttpStatus())
			.body(ApiResponse.of(SUCCESS_BID.getCode(), SUCCESS_BID.getMessage()));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<Page<BidInfoResponse>>> getMyBids(
		@PathVariable Long auctionId,
		@CurrentUser User loginUser,
		@PageableDefault(size = 15, sort = "createdAt", direction = Sort.Direction.DESC)
		Pageable pageable
	) {
		var responses = bidService.getMyBids(auctionId, loginUser, pageable);
		return ResponseEntity.status(SUCCESS_GET_ALL_BID.getHttpStatus())
			.body(
				ApiResponse.of(
					SUCCESS_GET_ALL_BID.getCode(),
					SUCCESS_GET_ALL_BID.getMessage(),
					responses
				)
			);
	}

	@GetMapping("/sse")
	public SseEmitter subscribe(@PathVariable Long auctionId) {
		return bidService.subscribe(auctionId);
	}
}
