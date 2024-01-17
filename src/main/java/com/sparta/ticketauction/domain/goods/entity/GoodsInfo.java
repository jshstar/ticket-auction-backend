package com.sparta.ticketauction.domain.goods.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import com.sparta.ticketauction.global.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "goods_info")
public class GoodsInfo extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("공연 제목")
	@Column(name = "name", length = 30, nullable = false)
	private String name;

	@Comment("공연 내용")
	@Column(name = "description", length = 150, nullable = false)
	private String description;

	@Comment("공연 시간")
	@Column(name = "running_time", nullable = false)
	@ColumnDefault("0")
	private Integer runningTime = 0;

	@Comment("연령대")
	@Column(name = "age_grade", nullable = false)
	private AgeGrade ageGrade;

	@Comment("공연 카테고리")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "goods_category_id")
	private GoodsCategory goodsCategory;

	@Comment("공연 이미지")
	@OneToMany(mappedBy = "goodsInfo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<GoodsImage> goodsImage = new ArrayList<>();

	@Comment("공연")
	@OneToMany(mappedBy = "goodsInfo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Goods> goods = new ArrayList<>();

	@Builder
	private GoodsInfo(
		String name,
		String description,
		Integer runningTime,
		Integer ageGrade
	) {
		this.name = name;
		this.description = description;
		this.runningTime = runningTime;
		this.ageGrade = AgeGrade.of(ageGrade);
	}

	public void addGoodsImage(List<GoodsImage> goodsImages) {
		this.goodsImage.addAll(goodsImages);
	}

	public void updateGoodsCategory(GoodsCategory goodsCategory) {
		this.goodsCategory = goodsCategory;
	}

	public void addGoods(Goods goods) {
		this.goods.add(goods);
	}

}
