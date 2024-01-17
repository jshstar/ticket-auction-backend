package com.sparta.ticketauction.domain.bid.repository;

import static com.sparta.ticketauction.domain.bid.entity.QBid.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.ticketauction.domain.bid.response.BidInfoResponse;
import com.sparta.ticketauction.domain.user.entity.User;

@Repository
public class BidRepositoryCustomImpl implements BidRepositoryCustom {
	private final JPAQueryFactory jpaQueryFactory;

	public BidRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
		this.jpaQueryFactory = jpaQueryFactory;
	}

	@Override
	public Page<BidInfoResponse> getMyBids(Long auctionId, User user, Pageable pageable) {
		List<BidInfoResponse> myBids = jpaQueryFactory
			.select(Projections.constructor(BidInfoResponse.class,
				bid.id,
				bid.auction.id,
				bid.price,
				bid.status,
				bid.createdAt
			))
			.from(bid)
			.where(
				bid.auction.id.eq(auctionId)
					.and(bid.user.eq(user))
			)
			.orderBy(sortBid(pageable))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = jpaQueryFactory.select(bid.count())
			.from(bid);

		//getPage -> count 쿼리 지연
		// 1. 첫 번째 페이지이면서 content 크기가 한 페이지의 사이즈보다 작을 때 (ex, content:3개, page 크기: 10)
		// 2. 마지막 페이지일 때 (getOffset이 0이 아니면서, content 크기가 한 페이지의 사이즈보다 작을 때)
		return PageableExecutionUtils.getPage(myBids, pageable, countQuery::fetchOne);
	}

	private OrderSpecifier<?> sortBid(Pageable pageable) {
		OrderSpecifier<?> orderSpecifier = null;
		for (Sort.Order o : pageable.getSort()) {
			Order direction = o.isAscending() ? Order.ASC : Order.DESC;
			if (o.getProperty().equals("createdAt")) {
				orderSpecifier = new OrderSpecifier<>(direction, bid.createdAt);
			}
		}
		return orderSpecifier;
	}
}
