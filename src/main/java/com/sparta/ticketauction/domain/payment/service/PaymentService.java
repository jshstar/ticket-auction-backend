package com.sparta.ticketauction.domain.payment.service;

import com.sparta.ticketauction.domain.payment.request.PaymentFromUserRequest;
import com.sparta.ticketauction.domain.payment.request.PaymentToApiRequest;
import com.sparta.ticketauction.domain.payment.response.PaymentSuccessResponse;
import com.sparta.ticketauction.domain.user.entity.User;

public interface PaymentService {
	PaymentToApiRequest requestPayment(User user, PaymentFromUserRequest request);

	PaymentSuccessResponse successPayment(String jsonBody);
}
