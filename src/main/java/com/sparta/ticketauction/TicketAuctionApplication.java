package com.sparta.ticketauction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class TicketAuctionApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketAuctionApplication.class, args);
	}
}
