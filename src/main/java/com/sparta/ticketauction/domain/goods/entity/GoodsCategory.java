package com.sparta.ticketauction.domain.goods.entity;

import org.hibernate.annotations.Comment;

import com.sparta.ticketauction.domain.admin.request.GoodsRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GoodsCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("카테고리 종류")
	@Column(name = "name")
	private String name;

	public GoodsCategory of(GoodsRequest goodsRequest) {
		return new GoodsCategory(goodsRequest.getCategoryName());
	}

	private GoodsCategory(String name) {
		this.name = name;
	}
}
