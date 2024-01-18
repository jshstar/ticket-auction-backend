package com.sparta.ticketauction.domain.goods.response;

import static com.sparta.ticketauction.domain.admin.service.AdminServiceImpl.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.goods.entity.GoodsImage;

import lombok.Getter;

@Getter
public class GoodsGetResponse {
	private final Long goodsId;

	private final String title;

	private final LocalDate startDate;

	private final LocalDate endDate;

	private final Integer runningTime;

	private final List<String> s3Urls;

	private final String ageGrade;

	private final Long placeId;

	private final String placeName;

	private final String placeAddress;

	public GoodsGetResponse(Goods goods) {
		this.goodsId = goods.getId();
		this.title = goods.getTitle();
		this.startDate = goods.getStartDate();
		this.endDate = goods.getEndDate();
		this.runningTime = goods.getGoodsInfo().getRunningTime();
		this.s3Urls = goods.getGoodsInfo().getGoodsImage().stream()
			.map(GoodsImage::getS3Key)
			.map(s3Key -> S3_PATH + s3Key)
			.collect(Collectors.toList());
		this.ageGrade = goods.getGoodsInfo().getAgeGrade().getKorea();
		this.placeId = goods.getPlace().getId();
		this.placeName = goods.getPlace().getName();
		this.placeAddress = goods.getPlace().getAddress();
	}
}
