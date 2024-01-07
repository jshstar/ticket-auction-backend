package com.sparta.ticketauction.domain.goods.entity;

import org.hibernate.annotations.Comment;

import com.sparta.ticketauction.domain.admin.request.GoodsRequest;
import com.sparta.ticketauction.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "goods_category")
public class GoodsCategory extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("카테고리 종류")
	@Column(name = "name", length = 10)
	private String name;

	public static GoodsCategory of(GoodsRequest goodsRequest) {
		return new GoodsCategory(goodsRequest.getCategoryName());
	}

	private GoodsCategory(String name) {
		this.name = name;
	}
}
