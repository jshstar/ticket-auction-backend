package com.sparta.ticketauction.domain.reservation.service;

import org.springframework.data.domain.Page;

import com.sparta.ticketauction.domain.auction.entity.Auction;
import com.sparta.ticketauction.domain.bid.entity.Bid;
import com.sparta.ticketauction.domain.reservation.request.ReservationCreateRequest;
import com.sparta.ticketauction.domain.reservation.response.ReservationDetailResponse;
import com.sparta.ticketauction.domain.reservation.response.ReservationResponse;
import com.sparta.ticketauction.domain.user.entity.User;

import jakarta.servlet.http.HttpServletRequest;

public interface ReservationService {

	/**
	 * 공연 좌석 예매 요청을 처리한다.
	 * 트랜잭션이 실패하면 이미 예매된 좌석일 가능성이 있다.
	 * @param user 실행 유저
	 * @param request 예매 정보
	 * @return 예매 정보를 반환한다.
	 */
	ReservationDetailResponse reserve(User user, ReservationCreateRequest request);

	/**
	 * 공연 좌석 경매 기록으로 예매 기록을 생성한다.
	 * @param bid 경매 낙찰한 입찰
	 * @param auction 경매 정보
	 * @return 예매 정보를 반환한다.
	 */
	ReservationDetailResponse reserve(Bid bid, Auction auction);

	/**
	 * 예매 상세 정보를 가져온다.
	 * @param user 실행 유저
	 * @param reservationId 예매 id
	 * @return 예매 상세 정보를 반환한다.
	 */
	ReservationDetailResponse getReservationDetailResponse(User user, Long reservationId);

	/**
	 * User가 예매한 기록들을 가져온다.
	 * @param user 실행 유저
	 * @param page 조회 페이지
	 * @param size 조회 사이즈
	 * @return 예매한 기록들을 반환한다.
	 */
	Page<ReservationResponse> searchReservations(User user, Integer page, Integer size);

	/**
	 * 예매를 취소한다.
	 * @param user 실행 유저
	 * @param reservationId 취소할 예매 id
	 */
	void cancelReservation(User user, Long reservationId);

	/**
	 * 인증용 QR Code 이미지를 Base64로 인코딩하여 생성한다.
	 * @param user 실행 유저
	 * @param reservationId 인증할 예매 id
	 * @param request HttpServletRequest
	 * @return Base64로 인코딩한 QR Code 이미지를 반환한다.
	 */
	String createQRCode(User user, Long reservationId, HttpServletRequest request);

	/**
	 * 예매 티켓 입장용 QR코드를 검사한다.
	 * 인증에 성공하지 못하면 ApiException을 반환한다.
	 * @param reservationId 인증할 예매 id
	 * @param authCode 6자리 숫자로 이루어진 인증 코드
	 */
	void authenticateReservation(Long reservationId, String authCode);
}
