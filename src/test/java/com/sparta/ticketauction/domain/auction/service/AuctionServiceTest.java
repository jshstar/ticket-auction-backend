package com.sparta.ticketauction.domain.auction.service;

import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.sparta.ticketauction.domain.auction.entity.Auction;
import com.sparta.ticketauction.domain.auction.repository.AuctionRepository;
import com.sparta.ticketauction.domain.auction.request.AuctionCreateRequest;
import com.sparta.ticketauction.domain.bid.entity.Bid;
import com.sparta.ticketauction.domain.bid.service.BidRedisService;
import com.sparta.ticketauction.domain.bid.service.BidServiceImpl;
import com.sparta.ticketauction.domain.grade.entity.Grade;
import com.sparta.ticketauction.domain.grade.entity.ZoneGrade;
import com.sparta.ticketauction.domain.grade.repository.ZoneGradeRepository;
import com.sparta.ticketauction.domain.reservation.service.ReservationService;
import com.sparta.ticketauction.domain.schedule.entity.Schedule;
import com.sparta.ticketauction.domain.schedule.repository.ScheduleRepository;
import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.util.UserUtil;
import com.sparta.ticketauction.global.exception.ApiException;
import com.sparta.ticketauction.global.exception.ErrorCode;

@DisplayName("[경매] 테스트")
@ExtendWith(MockitoExtension.class)
class AuctionServiceTest {
	@InjectMocks
	private AuctionServiceImpl sut;

	@Mock
	private AuctionRepository auctionRepository;

	@Mock
	private  ScheduleRepository scheduleRepository;

	@Mock
	private ZoneGradeRepository zoneGradeRepository;

	@Mock
	private ReservationService reservationService;

	@Mock
	private BidServiceImpl bidService;
	@Mock
	private BidRedisService bidRedisService;

	@Nested
	class 경매_등록_테스트 {
		@Test
		void 정상() {
			AuctionCreateRequest request = new AuctionCreateRequest(100);
			Grade grade = Grade.builder().auctionPrice(1000L).build();
			Schedule schedule = Schedule.builder().startDateTime(LocalDateTime.now()).build();
			ZoneGrade zoneGrade = ZoneGrade.builder().grade(grade).build();

			given(scheduleRepository.getReferenceById(any())).willReturn(schedule);
			given(zoneGradeRepository.getReferenceById(any())).willReturn(zoneGrade);

			//when
			sut.createAuction(1L, 1L, request);

			//then
			then(auctionRepository).should().save(any(Auction.class));
			then(bidRedisService).should().saveWithExpire(any(Auction.class));
		}
	}

	@Nested
	class 경매_종료_테스트 {
		@Test
		void 정상() {
			Long auctionId = 1L;
			Long bidId = 1L;
			Auction auction = getAuction(auctionId);
			Bid bid = getBid(auction, bidId);
			given(auctionRepository.findById(auctionId))
				.willReturn(Optional.of(auction));

			given(bidService.getCurrentBid(auction))
				.willReturn(Optional.of(bid));

			//when
			sut.endAuction(auctionId);

			//then
			then(reservationService).should().reserve(bid, auction);
		}

		@Test
		void 종료된_경매가_없는경우_실패() {
			//Given
			Long auctionId = 1L;
			given(auctionRepository.findById(auctionId))
				.willReturn(Optional.empty());

			//when && Then
			Assertions.assertThatThrownBy(() -> sut.endAuction(auctionId))
				.isInstanceOf(ApiException.class)
				.hasMessage(ErrorCode.NOT_FOUND_AUCTION.getMessage());

		}
		@Test
		void 낙찰자가_없는경우_실패() {
			//Given
			Long auctionId = 1L;
			Long bidId = 1L;
			Auction auction = getAuction(auctionId);
			Bid bid = getBid(auction, bidId);
			given(auctionRepository.findById(auctionId))
				.willReturn(Optional.of(auction));

			given(bidService.getCurrentBid(auction))
				.willReturn(Optional.empty());

			//when && Then
			Assertions.assertThatThrownBy(() -> sut.endAuction(auctionId))
				.isInstanceOf(ApiException.class)
				.hasMessage(ErrorCode.NOT_FOUND_WIN_BID.getMessage());

		}
	}

	private static Auction getAuction(Long auctionId) {
		Auction auction = Auction.builder()
			.seatNumber(1)
			.build();

		ReflectionTestUtils.setField(auction, "id", auctionId);
		return auction;
	}

	private static Bid getBid(Auction auction, Long bidId) {
		User bidWinner = UserUtil.createUser();
		Bid bid = Bid.builder()
			.auction(auction)
			.user(bidWinner)
			.build();

		ReflectionTestUtils.setField(bid, "id", bidId);
		return bid;
	}
}
