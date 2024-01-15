package com.sparta.ticketauction.domain.payment.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.ticketauction.domain.payment.request.PaymentFromUserRequest;
import com.sparta.ticketauction.domain.payment.request.PaymentToApiRequest;
import com.sparta.ticketauction.domain.payment.response.PaymentSuccessResponse;
import com.sparta.ticketauction.domain.payment.service.PaymentService;
import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.global.annotaion.CurrentUser;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

	private final PaymentService paymentService;

	@PostMapping
	public ResponseEntity<?> requestPayment(
		@CurrentUser User user,
		@RequestBody @Valid PaymentFromUserRequest request
	) {
		Map<String, Object> status = new HashMap<>();
		PaymentToApiRequest toApiRequest = paymentService.requestPayment(user, request);
		status.put("request", toApiRequest);

		return ResponseEntity.ok(status);
	}

	@PostMapping("/success")
	public ResponseEntity<?> successPayment(@RequestBody String jsonBody) throws Exception {
		PaymentSuccessResponse response = paymentService.successPayment(jsonBody);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/getKey")
	public ResponseEntity<?> getKey(@CurrentUser User user) {
		return ResponseEntity.ok(paymentService.getKey());
	}
}
