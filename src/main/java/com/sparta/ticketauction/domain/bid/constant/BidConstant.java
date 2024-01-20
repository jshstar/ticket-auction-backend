package com.sparta.ticketauction.domain.bid.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BidConstant {
	public static final double BID_PRICE_INCREASE_PERCENT = 0.05;
	public static final String AUCTION_BID_KEY_PREFIX = "auctions_bid_";
	public static final String AUCTION_SSE_PREFIX = "auction_sse_";
}
