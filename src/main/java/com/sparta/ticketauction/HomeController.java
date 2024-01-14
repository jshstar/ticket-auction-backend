package com.sparta.ticketauction;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/api/v1/")
	public String home() {
		return "index";
	}

}
