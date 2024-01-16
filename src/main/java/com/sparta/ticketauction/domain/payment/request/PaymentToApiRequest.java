package com.sparta.ticketauction.domain.payment.request;

import java.time.LocalDateTime;

import com.sparta.ticketauction.domain.payment.entity.Payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PaymentToApiRequest {

	private final Long amount;
	private final String orderName;
	private final String orderId;
	private final String userEmail;
	private final String userName;
	private final String successUrl;
	private final String failUrl;
	private final LocalDateTime createdAt;

	public static PaymentToApiRequest from(Payment payment, String successUrl, String failUrl) {
		return new PaymentToApiRequest(
			payment.getAmount(),
			payment.getOrderName(),
			payment.getOrderId(),
			payment.getUser().getEmail(),
			payment.getUser().getName(),
			successUrl,
			failUrl,
			payment.getCreatedAt()
		);
	}
}
