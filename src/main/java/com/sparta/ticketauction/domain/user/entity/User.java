package com.sparta.ticketauction.domain.user.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

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

	@Comment("회원 이메일")
	@Column(name = "email")
	private String email;

	@Comment("회원 비밀번호")
	@Column(name = "password")
	private String password;

	@Comment("회원 이름")
	@Column(name = "name")
	private String name;

	@Comment("회원 닉네임")
	@Column(name = "nickname")
	private String nickname;

	@Comment("회원 전화번호")
	@Column(name = "phone_number")
	private String phoneNumber;

	@Comment("회원 역할(관리자 or 일반 유저)")
	@Column(name = "role")
	private Role role = Role.USER;

	@Comment("회원 보유 포인트")
	@Column(name = "point")
	@ColumnDefault("0")
	private long point;
}
