package com.sparta.ticketauction.domain.goods.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Comment;

import com.sparta.ticketauction.domain.admin.request.GoodsRequest;
import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.global.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "goods")
public class Goods extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("공연 제목")
	@Column(name = "name", length = 30)
	private String name;

	@Comment("공연 내용")
	@Column(name = "description", length = 150)
	private String description;

	@Comment("공연 시작일")
	@Column(name = "start_date")
	private LocalDate startDate;

	@Comment("공연 마감일")
	@Column(name = "end_date")
	private LocalDate endDate;

	@Comment("연령대")
	@Column(name = "age_grade")
	private AgeGrade ageGrade;

	@Comment("공연 시간")
	@Column(name = "running_time")
	private int runningTime;

	@Comment("공연 카테고리")
	@ManyToOne
	@JoinColumn(name = "goods_category_id")
	private GoodsCategory goodsCategory;

	@Comment("공연장")
	@ManyToOne
	@JoinColumn(name = "place_id")
	private Place place;

	@Comment("공연 이미지")
	@OneToMany(mappedBy = "goods", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<GoodsImage> goodsImage = new ArrayList<>();

	public static Goods of(
		GoodsRequest goodsRequest,
		GoodsCategory goodsCategory,
		List<GoodsImage> goodsImage,
		Place place
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
			place
		);
	}

	private Goods(
		String name,
		String description,
		LocalDate startDate,
		LocalDate endDate,
		int ageGrade,
		int runningTime,
		GoodsCategory goodsCategory,
		List<GoodsImage> goodsImage,
		Place place
	) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.ageGrade = AgeGrade.of(ageGrade);
		this.runningTime = runningTime;
		this.goodsCategory = goodsCategory;
		this.goodsImage = goodsImage;
		this.place = place;
	}

}
