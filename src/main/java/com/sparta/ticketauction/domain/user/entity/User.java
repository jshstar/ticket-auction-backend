package com.sparta.ticketauction.domain.user.entity;

import org.hibernate.annotations.ColumnDefault;

import com.sparta.ticketauction.domain.user.entity.constant.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "name")
	private String name;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "role")
	private Role role = Role.USER;

	@Column(name = "point")
	@ColumnDefault("0")
	private long point;
}
