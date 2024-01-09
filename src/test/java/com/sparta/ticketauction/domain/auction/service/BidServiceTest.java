package com.sparta.ticketauction.domain.auction.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.sparta.ticketauction.domain.auction.entity.Auction;
import com.sparta.ticketauction.domain.auction.entity.Bid;
import com.sparta.ticketauction.domain.auction.repository.AuctionRepository;
import com.sparta.ticketauction.domain.auction.repository.BidRepository;
import com.sparta.ticketauction.domain.auction.request.BidRequest;
import com.sparta.ticketauction.domain.user.util.UserUtil;
import com.sparta.ticketauction.global.exception.ApiException;
import com.sparta.ticketauction.global.exception.ErrorCode;

@DisplayName("[경매] 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class BidServiceTest {
	@InjectMocks
	private BidServiceImpl sut;

	@Mock
	private AuctionRepository auctionRepository;

	@Mock
	private BidRepository bidRepository;

	@Mock
	private RedissonClient redissonClient;



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

		RAtomicLong atomicLong = mock(RAtomicLong.class);
		given(redissonClient.getAtomicLong(any()))
			.willReturn(atomicLong);

		given(auctionRepository.getReferenceById(auctionId))
			.willReturn(auction);
		//When
		sut.bid(auctionId, bidRequest, UserUtil.createUser());

		//Then
		then(bidRepository).should().save(any(Bid.class));
		then(atomicLong).should().set(bidRequest.getPrice());
	}

	@Test
	void 입찰하기_하회입찰_실패_테스트() throws Exception {
		//Given
		Long auctionId = 1L;
		BidRequest bidRequest = new BidRequest(10_000L);

		RAtomicLong atomicLong = mock(RAtomicLong.class);
		given(redissonClient.getAtomicLong(any()))
			.willReturn(atomicLong);

		given(atomicLong.get())
			.willReturn(1000_000L);

		//When & Then
		assertThatThrownBy(() -> sut.bid(auctionId, bidRequest, UserUtil.createUser()))
			.isInstanceOf(ApiException.class)
			.hasMessage(ErrorCode.BAD_REQUEST_BID.getMessage());
	}

	@Test
	void 입찰하기_경매종료_테스트() throws Exception {
		//Given
		Long auctionId = 1L;
		BidRequest bidRequest = new BidRequest(10_0000L);

		RAtomicLong atomicLong = mock(RAtomicLong.class);
		given(redissonClient.getAtomicLong(any()))
			.willReturn(atomicLong);

		given(atomicLong.remainTimeToLive())
			.willReturn(0L);

		//When & Then
		assertThatThrownBy(() -> sut.bid(auctionId, bidRequest, UserUtil.createUser()))
			.isInstanceOf(ApiException.class)
			.hasMessage(ErrorCode.ENDED_AUCTION.getMessage());
	}
}