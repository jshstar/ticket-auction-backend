package com.sparta.ticketauction.domain.goods.entity;

import org.hibernate.annotations.Comment;

import com.sparta.ticketauction.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "goods_image")
public class GoodsImage extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("S3 URL")
	@Column(name = "s3_key", length = 150, nullable = false)
	private String s3Key;

	@Comment("대표 이미지 or 일반 이미지")
	@Column(name = "type", length = 10, nullable = false)
	@Enumerated(value = EnumType.STRING)
	private ImageType type;

	@Comment("상품 id")
	@ManyToOne
	@JoinColumn(name = "goods_info_id")
	private GoodsInfo goodsInfo;

	@Builder
	private GoodsImage(String s3Key, String type, GoodsInfo goodsInfo) {
		this.s3Key = s3Key;
		this.type = ImageType.of(type);
		this.goodsInfo = goodsInfo;
	}
}
