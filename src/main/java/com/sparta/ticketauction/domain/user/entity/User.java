package com.sparta.ticketauction.domain.user.entity;

import com.sparta.ticketauction.domain.user.entity.constant.Role;


public class User {

	private Long id;
	private String email;
	private String password;
	private String name;
	private String nickname;
	private String phoneNumber;
	private Role role;
}
