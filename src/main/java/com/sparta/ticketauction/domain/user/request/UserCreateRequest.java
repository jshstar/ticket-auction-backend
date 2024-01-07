package com.sparta.ticketauction.domain.user.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserCreateRequest {

	@NotBlank(message = "필수 입력입니다.")
	@Email(message = "이메일 형식으로 입력해주세요.")
	private final String email;

	@Pattern(
		regexp = "^(?=.*?[a-zA-Z])(?=.*?[0-9])(?=.*?[#!@$ %^])[a-zA-Z0-9@#$%^!]*$",
		message = "영어, 숫자, 특수문자(!,@,#,$,%,^) 조합으로 입력해주세요."
	)
	@Size(min = 8, max = 15, message = "최소 8자, 최대 15자로 입력해주세요.")
	private final String password;

	@Pattern(regexp = "^[가-힣]+$", message = "한글로만 입력해주세요.")
	@Size(min = 2, max = 10, message = "최소 2자, 최대 10자로 입력해주세요.")
	private final String name;

	@Pattern(regexp = "^[가-힣]+$", message = "한글로만 입력해주세요.")
	@Size(min = 2, max = 10, message = "최소 2자, 최대 10자로 입력해주세요.")
	private final String nickname;

	@NotBlank(message = "필수 입력입니다.")
	@Pattern(regexp = "^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$", message = "전화번호 형식으로 입력해주세요.")
	private final String phoneNumber;

	@NotNull(message = "필수 입력입니다.")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private final LocalDate birth;
}
