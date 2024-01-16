package com.sparta.ticketauction.domain.payment.entity;

import org.hibernate.annotations.Comment;

import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.global.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "payments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("결제 키")
	@Column(name = "payment_key")
	private String paymentKey;

	// @Comment("결제 상태")
	// @Column(name = "status", nullable = false)
	// private String status;

	@Comment("주문 번호")
	@Column(name = "order_id", nullable = false)
	private String orderId;

	@Comment("결제 금액")
	@Column(name = "amount", nullable = false)
	private Long amount;

	@Comment("주문 이름")
	@Column(name = "order_name", nullable = false)
	private String orderName;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id")
	private User user;

	@Builder
	private Payment(String paymentKey, String orderId, Long amount, String orderName, User user) {
		this.paymentKey = paymentKey;
		this.orderId = orderId;
		this.amount = amount;
		this.orderName = orderName;
		this.user = user;
	}

	public void addPaymentKey(String paymentKey) {
		this.paymentKey = paymentKey;
	}
}
