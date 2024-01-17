package com.sparta.ticketauction.domain.bid.constant;

import lombok.Getter;

@Getter
public enum BidStatus {
	SUCCESS("낙찰"), //경매가 종료됬을 때 가장 상회입찰일 경우
	PROCESS("진행 중"), //경매 진행 중에 상회입찰인 경우
	FAILED("유찰"); //누군가 입찰한 경우

	private final String ko;

	BidStatus(String ko) {
		this.ko = ko;
	}
}
