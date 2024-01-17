package com.sparta.ticketauction.domain.goods.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.sparta.ticketauction.domain.admin.request.GoodsInfoCreateRequest;
import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.goods.entity.GoodsCategory;
import com.sparta.ticketauction.domain.goods.entity.GoodsImage;
import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;
import com.sparta.ticketauction.domain.goods.response.GoodsInfoGetResponse;
import com.sparta.ticketauction.domain.goods.response.GoodsInfoGetSliceResponse;

public interface GoodsInfoService {

	// 공연 카테고리 생성
	GoodsCategory createGoodsCategory(String name);

	// 공연 이미지 생성
	List<GoodsImage> createGoodsImage(List<MultipartFile> multipartFiles, GoodsInfo goodsInfo);

	// 공연 정보 생성
	GoodsInfo createGoodsInfo(GoodsInfoCreateRequest goodsInfoCreateRequest);

	// 공연 이미지 s3 업로드
	List<String> s3Upload(List<MultipartFile> multipartFiles, Long goodsInfo);

	// 공연 이미지 타입 분리
	List<GoodsImage> divideTypeGoodsImageList(List<String> fileKeyList, GoodsInfo goodsInfo);

	// 공연 이미지 타입 체크
	String checkGoodsType(String type);

	// 공연 정보 단건 조회
	GoodsInfoGetResponse getGoodsInfo(Long goodsInfoId);

	// 공연 정보 카테고리별 페이징 페이징 조회
	GoodsInfoGetSliceResponse getSliceGoodsInfo(Pageable pageable, String categoryName);

	// 공연 체크 이미 끝난 공연은 제외
	List<Goods> checkGoods(GoodsInfo goodsInfo);

	GoodsInfo findByGoodsInfoId(Long goodsInfoId);

}
