package com.sparta.ticketauction.domain.goods.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sparta.ticketauction.domain.admin.request.GoodsCreateRequest;
import com.sparta.ticketauction.domain.admin.request.GoodsInfoCreateRequest;
import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.goods.entity.GoodsCategory;
import com.sparta.ticketauction.domain.goods.entity.GoodsImage;
import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;
import com.sparta.ticketauction.domain.goods.response.GoodsAuctionSeatInfoResponse;
import com.sparta.ticketauction.domain.goods.response.GoodsCategoryGetResponse;
import com.sparta.ticketauction.domain.goods.response.GoodsGetCursorResponse;
import com.sparta.ticketauction.domain.goods.response.GoodsGetResponse;
import com.sparta.ticketauction.domain.goods.response.GoodsInfoGetResponse;
import com.sparta.ticketauction.domain.goods.response.GoodsSeatInfoResponse;
import com.sparta.ticketauction.domain.place.entity.Place;

public interface GoodsService {

	// 공연 생성
	Goods createGoods(GoodsCreateRequest goodsCreateRequest, Place place, GoodsInfo goodsInfo);

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

	// 전체 공연 정보 조회
	List<GoodsInfoGetResponse> getAllGoodsInfo();

	// 전체 공연 페이징 조회
	GoodsGetCursorResponse getGoodsWithCursor(Long cursorId, int size, String categoryName);

	// 공연 단건 조회
	GoodsGetResponse getGoods(Long goodsId);

	// 공연 카테고리 전체 조회
	List<GoodsCategoryGetResponse> getAllGoodsCategory();

	// 공연 정보 조회
	GoodsInfo findByGoodsInfoId(Long goodsInfoId);

	// 공연 조회
	Goods findByGoodsId(Long goodsId);

	// 공연 좌석 정보 조회
	GoodsSeatInfoResponse findGoodsSeatInfo(Long goodsId);

	GoodsAuctionSeatInfoResponse findGoodsAuctionSeatInfo(Long scheduleId, Long goodsId);

	// Redis에 저장되어 있는 공연 캐쉬 정보 삭제
	void evictCacheForCategory(String categoryName);

	// Redis에 저장되어 있는 공연카테고리 캐쉬 정보 삭제
	void clearGoodsCategoryCache();
}
