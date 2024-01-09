package com.sparta.ticketauction.domain.user.entity;

import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import com.sparta.ticketauction.domain.user.entity.constant.Role;
import com.sparta.ticketauction.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("회원 이메일")
	@Column(name = "email", length = 50, nullable = false)
	private String email;

	@Comment("회원 비밀번호")
	@Column(name = "password", nullable = false)
	private String password;

	@Comment("회원 이름")
	@Column(name = "name", length = 10, nullable = false)
	private String name;

	@Comment("회원 닉네임")
	@Column(name = "nickname", length = 10, nullable = false)
	private String nickname;

	@Comment("회원 전화번호")
	@Column(name = "phone_number", length = 30, nullable = false)
	private String phoneNumber;

	@Comment("회원 생년월일")
	@Column(name = "birth", nullable = false)
	private LocalDate birth;

	@Comment("회원 역할(관리자 or 일반 유저)")
	@Column(name = "role", nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role = Role.USER;

	@Comment("회원 보유 포인트")
	@Column(name = "point", nullable = false)
	@ColumnDefault("0")
	private Long point = 0L;

	@Comment("삭제 여부")
	@Column(name = "is_deleted", nullable = false)
	@ColumnDefault("false")
	private Boolean isDeleted = false;

	@Builder
	private User(Long id, String email, String password, String name, String nickname, String phoneNumber,
		LocalDate birth) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.nickname = nickname;
		this.phoneNumber = phoneNumber;
		this.birth = birth;
	}

	public void chargePoint(Long point) {
		this.point += point;
	}

	public void usePoint(Long point) {
		this.point -= point;
	}
}
