package com.sparta.ticketauction.domain.goods.entity;

import java.time.LocalDateTime;
import java.util.List;

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

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "start_date")
	private LocalDateTime startDate;

	@Column(name = "end_date")
	private LocalDateTime endDate;

	@Column(name = "age_grade")
	private AgeGrade ageGrade;

	@Column(name = "running_time")
	private String runningTime;

	@ManyToOne
	@JoinColumn(name = "goods_category_id")
	private GoodsCategory goodsCategory;

	@ManyToOne
	@JoinColumn(name = "places_id")
	private Places places;

	@OneToMany(mappedBy = "goods", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<GoodsImage> goodsImage;

	public static Goods of(GoodsRequest goodsRequest, GoodsCategory goodsCategory, List<GoodsImage> goodsImage,
		Places places) {
		return new Goods(
			goodsRequest.getName(),
			goodsRequest.getDescription(),
			goodsRequest.getStartDate(),
			goodsRequest.getEndDate(),
			goodsRequest.getAgeGrade(),
			goodsRequest.getRunningTime(),
			goodsCategory,
			goodsImage,
			places);
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
