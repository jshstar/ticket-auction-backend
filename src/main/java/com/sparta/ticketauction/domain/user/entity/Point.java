package com.sparta.ticketauction.domain.user.entity;

import org.hibernate.annotations.Comment;

import com.sparta.ticketauction.domain.user.enums.PointType;
import com.sparta.ticketauction.global.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "point_logs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("회원")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Comment("변동된 포인트")
	private Long point;

	@Comment("변동 타입")
	@Enumerated(EnumType.STRING)
	private PointType type;

	@Builder
	public Point(User user, Long point, PointType type) {
		this.user = user;
		this.point = point;
		this.type = type;
	}
}
