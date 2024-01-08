package com.sparta.ticketauction.domain.sequence.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "sequence")
public class Sequence extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("회차 수")
	@Column(name = "sequence")
	@ColumnDefault("0")
	private Integer sequence = 0;

	@Comment("공연 일시")
	@Column(name = "start_date_time")
	private LocalDateTime startDateTime;

	@Comment("공연 id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "goods_id")
	private Goods goods;

	@Builder
	private Sequence(int sequence, LocalDateTime startDateTime, Goods goods) {
		this.sequence = sequence;
		this.startDateTime = startDateTime;
		this.goods = goods;
	}
}
