package com.sparta.ticketauction.domain.bid.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.sparta.ticketauction.domain.auction.entity.Auction;
import com.sparta.ticketauction.domain.auction.repository.AuctionRepository;
import com.sparta.ticketauction.domain.bid.entity.Bid;
import com.sparta.ticketauction.domain.bid.repository.BidRepository;
import com.sparta.ticketauction.domain.bid.request.BidRequest;
import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.util.UserUtil;
import com.sparta.ticketauction.global.exception.ApiException;
import com.sparta.ticketauction.global.exception.ErrorCode;

@DisplayName("[입찰] 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class BidServiceTest {
	@InjectMocks
	private BidServiceImpl sut;

	@Mock
	private AuctionRepository auctionRepository;

	@Mock
	private BidRepository bidRepository;

	@Mock
	private BidRedisService bidRedisService;


	//로그인한 유저가
	//특정 경매에 입찰금액을 적고 입찰하기 버튼을 눌렀을때.
	//해당 경매에 대한 입찰 기록을 저장하고
	//경매의 현재가 최신화를 해줘야함.
	@Test
	void 입찰하기_정상_테스트() throws Exception {
		//Given
		Long auctionId = 1L;
		BidRequest bidRequest = new BidRequest(10_0000L);
		Auction auction = Auction.builder()
			.startDateTime(LocalDateTime.now())
			.endDateTime(LocalDateTime.now().plusDays(7))
			.startPrice(1000L)
			.build();

		User bidder = UserUtil.createUser();
		ReflectionTestUtils.setField(bidder, "point", 10_0000L);

		System.out.println(bidder.getPoint());
		given(bidRedisService.isExpired(any()))
			.willReturn(false);

		given(bidRedisService.getBidPrice(any()))
			.willReturn(1000L);

		given(auctionRepository.findById(auctionId))
			.willReturn(Optional.of(auction));

		//When
		sut.bid(auctionId, bidRequest, bidder);

		//Then
		then(bidRepository).should().save(any(Bid.class));
		then(bidRedisService).should().setBidPrice(any(), any());
	}

	@Test
	void 입찰하기_하회입찰_실패_테스트() throws Exception {
		//Given
		Long auctionId = 1L;
		BidRequest bidRequest = new BidRequest(10_000L);

		User bidder = UserUtil.createUser();
		ReflectionTestUtils.setField(bidder, "point", 10_0000L);

		given(bidRedisService.isExpired(any()))
			.willReturn(false);

		given(bidRedisService.getBidPrice(any()))
			.willReturn(1000_000L);

		//When & Then
		assertThatThrownBy(() -> sut.bid(auctionId, bidRequest, bidder))
			.isInstanceOf(ApiException.class)
			.hasMessage(ErrorCode.BAD_REQUEST_BID.getMessage());
	}

	@Test
	void 입찰하기_경매종료_테스트() throws Exception {
		//Given
		Long auctionId = 1L;
		BidRequest bidRequest = new BidRequest(10_0000L);

		given(bidRedisService.isExpired(any()))
			.willReturn(true);

		//When & Then
		assertThatThrownBy(() -> sut.bid(auctionId, bidRequest, UserUtil.createUser()))
			.isInstanceOf(ApiException.class)
			.hasMessage(ErrorCode.ENDED_AUCTION.getMessage());
	}
}