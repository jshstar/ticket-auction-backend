package com.sparta.ticketauction.domain.goods.entity;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GoodsImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("S3 URL")
	@Column(name = "key")
	private String key;

	@Comment("대표 이미지 or 일반 이미지")
	@Column(name = "type")
	private ImageType type;

	@Comment("상품 id")
	@ManyToOne
	@JoinColumn(name = "goods_id")
	private Goods goods;

	public GoodsImage of(String key, String type) {
		return new GoodsImage(key, type);
	}

	private GoodsImage(String key, String type) {
		this.key = key;
		this.type = ImageType.of(type);
	}
}
