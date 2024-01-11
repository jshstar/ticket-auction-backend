package com.sparta.ticketauction.global.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionEndEvent {
	private Long id;

	@Builder
	public AuctionEndEvent(Long id) {
		this.id = id;
	}
}
