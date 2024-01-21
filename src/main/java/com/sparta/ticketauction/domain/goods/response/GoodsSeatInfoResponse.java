package com.sparta.ticketauction.domain.goods.response;

import java.util.List;

import com.sparta.ticketauction.domain.seat.response.SeatInfoResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GoodsSeatInfoResponse {

	List<SeatInfoResponse> seatInfos;
}
