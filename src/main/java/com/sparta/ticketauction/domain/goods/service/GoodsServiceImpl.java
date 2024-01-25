package com.sparta.ticketauction.domain.goods.service;

import static com.sparta.ticketauction.domain.admin.service.AdminServiceImpl.*;
import static com.sparta.ticketauction.global.exception.ErrorCode.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sparta.ticketauction.domain.admin.request.GoodsCreateRequest;
import com.sparta.ticketauction.domain.admin.request.GoodsInfoCreateRequest;
import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.goods.entity.GoodsCategory;
import com.sparta.ticketauction.domain.goods.entity.GoodsImage;
import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;
import com.sparta.ticketauction.domain.goods.repository.GoodsCategoryRepository;
import com.sparta.ticketauction.domain.goods.repository.GoodsImageRepository;
import com.sparta.ticketauction.domain.goods.repository.GoodsInfoRepository;
import com.sparta.ticketauction.domain.goods.repository.GoodsRepository;
import com.sparta.ticketauction.domain.goods.response.GoodsAuctionSeatInfoResponse;
import com.sparta.ticketauction.domain.goods.response.GoodsCategoryGetResponse;
import com.sparta.ticketauction.domain.goods.response.GoodsGetCursorResponse;
import com.sparta.ticketauction.domain.goods.response.GoodsGetQueryResponse;
import com.sparta.ticketauction.domain.goods.response.GoodsGetResponse;
import com.sparta.ticketauction.domain.goods.response.GoodsInfoGetResponse;
import com.sparta.ticketauction.domain.goods.response.GoodsSeatInfoResponse;
import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.global.exception.ApiException;
import com.sparta.ticketauction.global.util.S3Uploader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

	private final GoodsInfoRepository goodsInfoRepository;

	private final GoodsImageRepository goodsImageRepository;

	private final S3Uploader s3Uploader;

	private final GoodsCategoryRepository goodsCategoryRepository;

	public final GoodsRepository goodsRepository;

	@Override
	public Goods createGoods(GoodsCreateRequest goodsCreateRequest, Place place, GoodsInfo goodsInfo) {
		Goods goods = goodsCreateRequest.toEntity(place, goodsInfo);

		return goodsRepository.save(goods);
	}

	// 공연 정보 생성
	@Override
	public GoodsInfo createGoodsInfo(GoodsInfoCreateRequest goodsInfoCreateRequest) {
		GoodsInfo goodsInfo = goodsInfoCreateRequest.toEntity();

		return goodsInfoRepository.save(goodsInfo);
	}

	// 공연 이미지 생성
	@Override
	public List<GoodsImage> createGoodsImage(List<MultipartFile> multipartFiles, GoodsInfo goodsInfo) {
		List<String> fileUrl = s3Upload(multipartFiles, goodsInfo.getId());

		List<GoodsImage> goodsImageList = divideTypeGoodsImageList(fileUrl, goodsInfo);

		return goodsImageRepository.saveAll(goodsImageList);
	}

	// 공연 이미지 s3 업로드
	@Override
	public List<String> s3Upload(List<MultipartFile> multipartFiles, Long goodsInfoId) {
		List<String> fileUrl = new ArrayList<>();

		String thumbnailFilePath = FILE_PATH + THUMBNAIL + goodsInfoId;
		String generalFilePath = FILE_PATH + GENERAL + goodsInfoId;

		MultipartFile thumbnailMultipartFile = multipartFiles.get(0);
		multipartFiles.remove(0);

		String thumbnailUrl = s3Uploader.uploadSingleFileToS3(thumbnailMultipartFile, thumbnailFilePath);
		fileUrl.add(thumbnailUrl);

		List<String> generalUrl = s3Uploader.uploadFileToS3(multipartFiles, generalFilePath);
		fileUrl.addAll(generalUrl);

		return fileUrl;
	}

	// 공연 이미지 타입 분리
	@Override
	public List<GoodsImage> divideTypeGoodsImageList(List<String> fileKeyList, GoodsInfo goodsInfo) {
		List<GoodsImage> returnGoodsIamgeList = new ArrayList<>();
		for (String fileKey : fileKeyList) {
			GoodsImage goodsImage =
				GoodsImage
					.builder()
					.s3Key(fileKey)
					.type(this.checkGoodsType(fileKey))
					.goodsInfo(goodsInfo)
					.build();
			returnGoodsIamgeList.add(goodsImage);
		}
		return returnGoodsIamgeList;
	}

	// 공연 이미지 타입 체크
	@Override
	public String checkGoodsType(String type) {
		if (type.contains(THUMBNAIL)) {
			return "대표";
		}
		return "일반";
	}

	// 카테고리 생성 기타 입력시
	@Override
	public GoodsCategory createGoodsCategory(String name) {
		GoodsCategory goodsCategory = goodsCategoryRepository.findByName(name)
			.orElse(
				GoodsCategory
					.builder()
					.name(name)
					.build());

		return goodsCategoryRepository.save(goodsCategory);
	}

	// 공연 전체 정보 조회
	@Override
	@Transactional(readOnly = true)
	public List<GoodsInfoGetResponse> getAllGoodsInfo() {
		List<GoodsInfo> goodsInfos = goodsInfoRepository.findAll();
		return goodsInfos.stream().map(GoodsInfoGetResponse::new).toList();
	}

	// 공연 단건 조회
	@Override
	@Transactional(readOnly = true)
	public GoodsGetResponse getGoods(Long goodsId) {
		Goods goods = findByGoodsId(goodsId);
		return new GoodsGetResponse(goods);
	}

	// 공연 정보 카테고리별 페이징 페이징 조회
	@Override
	@Transactional(readOnly = true)
	public GoodsGetCursorResponse getGoodsWithCursor(Long cursorId, int size, String categoryName) {
		List<GoodsGetQueryResponse> goodsGetQueryResponses =
			goodsRepository.findAllByGoodsAndCategoryName(
				cursorId,
				size,
				categoryName
			);
		Long nextCursorId = -1L;

		if (!goodsGetQueryResponses.isEmpty()) {
			int lastSize = goodsGetQueryResponses.size() - 1;
			nextCursorId = goodsGetQueryResponses.get(lastSize).getGoodsId();
		}

		return new GoodsGetCursorResponse(goodsGetQueryResponses, nextCursorId);
	}

	// 공연 카테고리 전체 조회
	@Override
	public List<GoodsCategoryGetResponse> getAllGoodsCategory() {
		List<GoodsCategory> goodsCategorieList = goodsCategoryRepository.findAll();
		return goodsCategorieList.stream().map(GoodsCategoryGetResponse::new).toList();
	}

	// 공연 정보 조회
	@Override
	public GoodsInfo findByGoodsInfoId(Long goodsInfoId) {
		return goodsInfoRepository.findById(goodsInfoId)
			.orElseThrow(() -> new ApiException(NOT_FOUND_GOODS_INFO));
	}

	// 공연 조회
	public Goods findByGoodsId(Long goodsId) {
		return goodsRepository.findById(goodsId)
			.orElseThrow(() -> new ApiException(NOT_FOUND_GOODS));
	}

	@Override
	public GoodsSeatInfoResponse findGoodsSeatInfo(Long goodsId) {
		return GoodsSeatInfoResponse.builder()
			.seatInfos(goodsRepository.findGoodsSeatInfo(goodsId))
			.build();
	}

	@Override
	public GoodsAuctionSeatInfoResponse findGoodsAuctionSeatInfo(Long scheduleId, Long goodsId) {
		return GoodsAuctionSeatInfoResponse.builder()
			.seatInfos(goodsRepository.findGoodsAuctionSeatInfo(scheduleId, goodsId))
			.build();
	}
}
