package com.sparta.ticketauction.domain.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserCreateRequest {

	@NotBlank
	@Email
	private String email;

	@NotBlank
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$")
	private String password;

	@NotBlank
	@Pattern(regexp = "^[가-힣]+$")
	private String name;

	@NotBlank
	@Pattern(regexp = "^[가-힣]+$")
	private String nickname;

	@NotBlank
	@Pattern(regexp = "^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$")
	private String phoneNumber;
}
