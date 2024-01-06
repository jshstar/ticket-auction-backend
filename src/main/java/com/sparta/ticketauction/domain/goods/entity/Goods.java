package com.sparta.ticketauction.domain.goods.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Comment;

import com.sparta.ticketauction.domain.admin.request.GoodsRequest;
import com.sparta.ticketauction.domain.places.entity.Places;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Goods {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("공연 제목")
	@Column(name = "name")
	private String name;

	@Comment("공연 내용")
	@Column(name = "description")
	private String description;

	@Comment("공연 시작일")
	@Column(name = "start_date")
	private LocalDateTime startDate;

	@Comment("공연 마감일")
	@Column(name = "end_date")
	private LocalDateTime endDate;

	@Comment("연령대")
	@Column(name = "age_grade")
	private AgeGrade ageGrade;

	@Comment("공연 시간")
	@Column(name = "running_time")
	private String runningTime;

	@Comment("공연 카테고리")
	@ManyToOne
	@JoinColumn(name = "goods_category_id")
	private GoodsCategory goodsCategory;

	@Comment("공연장")
	@ManyToOne
	@JoinColumn(name = "places_id")
	private Places places;

	@Comment("공연 이미지")
	@OneToMany(mappedBy = "goods", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<GoodsImage> goodsImage = new ArrayList<>();

	public static Goods of(
		GoodsRequest goodsRequest,
		GoodsCategory goodsCategory,
		List<GoodsImage> goodsImage,
		Places places
	) {
		return new Goods(
			goodsRequest.getName(),
			goodsRequest.getDescription(),
			goodsRequest.getStartDate(),
			goodsRequest.getEndDate(),
			goodsRequest.getAgeGrade(),
			goodsRequest.getRunningTime(),
			goodsCategory,
			goodsImage,
			places
		);
	}

	private Goods(
		String name,
		String description,
		LocalDateTime startDate,
		LocalDateTime endDate,
		int ageGrade,
		String runningTime,
		GoodsCategory goodsCategory,
		List<GoodsImage> goodsImage,
		Places places
	) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.ageGrade = AgeGrade.of(ageGrade);
		this.runningTime = runningTime;
		this.goodsCategory = goodsCategory;
		this.goodsImage = goodsImage;
		this.places = places;
	}

}
