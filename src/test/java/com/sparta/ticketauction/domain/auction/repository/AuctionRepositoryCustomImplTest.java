package com.sparta.ticketauction.domain.auction.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import com.sparta.ticketauction.domain.auction.entity.Auction;
import com.sparta.ticketauction.domain.auction.response.AuctionInfoResponse;
import com.sparta.ticketauction.domain.bid.entity.Bid;
import com.sparta.ticketauction.domain.bid.repository.BidRepository;
import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.repository.UserRepository;
import com.sparta.ticketauction.domain.user.util.UserUtil;
import com.sparta.ticketauction.global.config.AuditingConfig;
import com.sparta.ticketauction.global.config.QueryDslConfig;

@DataJpaTest
@ActiveProfiles("test")
@Import({QueryDslConfig.class, AuditingConfig.class})
class AuctionRepositoryCustomImplTest {
	@Autowired
	BidRepository bidRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AuctionRepository auctionRepository;

	@BeforeEach
	void setUp() {
		User user1 = UserUtil.AMDIN_USER;
		User user2 = UserUtil.TEST_USER;
		userRepository.save(user1);
		userRepository.save(user2);

		Auction auction1 = auctionRepository.save(getAuction(1L, 1));
		Auction auction2 = auctionRepository.save(getAuction(2L, 2));

		Bid bid1 = getBid(1L, auction1, user1, 1000L, 1);
		bidRepository.save(bid1);

		Bid bid2 = getBid(2L, auction2, user1, 2000L, 2);
		bidRepository.save(bid2);

		Bid bid3 = getBid(3L, auction1, user2, 3000L, 3);
		bidRepository.save(bid3);

		Bid bid4 = getBid(4L, auction2, user2, 4000L, 4);
		bidRepository.save(bid4);
	}

	@Test
	void 참가한_경매_내역_조회시_경매_시작일_내림차순으로_정렬되어_조회한다() {
		//given
		Pageable pageable = PageRequest.of(
			0,
			10,
			Sort.Direction.DESC, "startDateTime"
		);
		User user = UserUtil.getUser();

		//when
		Page<AuctionInfoResponse> responses = auctionRepository.getJoinedMyAuctions(user, pageable);

		//then
		assertThat(responses.getContent()).hasSize(2);
		assertThat(responses.getContent().get(0).getId()).isEqualTo(2L);
		assertThat(responses.getContent().get(1).getId()).isEqualTo(1L);
		System.out.println(responses.getContent().get(0).getStartDateTime());
		System.out.println(responses.getContent().get(1).getStartDateTime());
	}

	private static Bid getBid(Long bidId, Auction auction, User user, Long price, int plusDays) {
		Bid bid = Bid.builder()
			.price(price)
			.user(user)
			.auction(auction)
			.build();

		ReflectionTestUtils.setField(bid, "id", bidId);
		ReflectionTestUtils.setField(bid, "createdAt", LocalDateTime.now().plusDays(plusDays));
		return bid;
	}

	private static Auction getAuction(Long auctionId, int plusDays) {
		Auction auction = Auction.builder()
			.seatNumber(1)
			.startPrice(1000L)
			.endDateTime(LocalDateTime.now())
			.build();

		ReflectionTestUtils.setField(auction, "id", auctionId);
		ReflectionTestUtils.setField(auction, "startDateTime", LocalDateTime.now().plusDays(plusDays));
		return auction;
	}
}