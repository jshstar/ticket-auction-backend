package com.sparta.ticketauction.domain.goods.entity;

import java.time.LocalDate;

import org.hibernate.annotations.Comment;

import com.sparta.ticketauction.domain.place.entity.Place;
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
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "goods")
public class Goods extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("공연 제목")
	@Column(name = "title", nullable = false)
	private String title;

	@Comment("공연 시작일")
	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Comment("공연 마감일")
	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

	@Comment("공연")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "goods_info_id")
	private GoodsInfo goodsInfo;

	@Comment("공연장")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "place_id")
	private Place place;

	@Builder
	private Goods(String title, LocalDate startDate, LocalDate endDate, GoodsInfo goodsInfo, Place place) {
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.goodsInfo = goodsInfo;
		this.place = place;
	}

}
