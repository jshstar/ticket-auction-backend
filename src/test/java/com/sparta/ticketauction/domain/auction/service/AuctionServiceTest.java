package com.sparta.ticketauction.domain.auction.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("[경매] 테스트")
@ExtendWith(MockitoExtension.class)
class AuctionServiceTest {
	@InjectMocks
	private AuctionServiceImpl sut;

	@Mock
	private AuctionRepository auctionRepository;
	@Mock
	private ReservationService reservationService;

	@Mock
	private BidServiceImpl bidService;

	@Nested
	class 경매_등록_테스트 {
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

			then(reservationService).should().reserve(auction, bid.getUser());
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

			then(reservationService).should().reserve(auction, bid.getUser());
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
			.startDateTime(LocalDateTime.now())
			.endDateTime(LocalDateTime.now().plusDays(7))
			.startPrice(1000L)
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
