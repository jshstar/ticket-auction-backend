package com.sparta.ticketauction.domain.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/users")
public class UserViewController {

	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
