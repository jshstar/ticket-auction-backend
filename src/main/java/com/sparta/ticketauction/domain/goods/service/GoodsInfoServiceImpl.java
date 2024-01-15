package com.sparta.ticketauction.domain.goods.service;

import static com.sparta.ticketauction.domain.admin.service.AdminServiceImpl.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sparta.ticketauction.domain.admin.request.GoodsRequest;
import com.sparta.ticketauction.domain.goods.entity.GoodsCategory;
import com.sparta.ticketauction.domain.goods.entity.GoodsImage;
import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;
import com.sparta.ticketauction.domain.goods.repository.GoodsCategoryRepository;
import com.sparta.ticketauction.domain.goods.repository.GoodsImageRepository;
import com.sparta.ticketauction.domain.goods.repository.GoodsInfoRepository;
import com.sparta.ticketauction.global.util.S3Uploader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoodsInfoServiceImpl implements GoodsInfoService {
	private final GoodsInfoRepository goodsInfoRepository;

	private final GoodsImageRepository goodsImageRepository;

	private final S3Uploader s3Uploader;

	private final GoodsCategoryRepository goodsCategoryRepository;

	// 공연 정보 생성
	@Override
	public GoodsInfo createGoodsInfo(GoodsRequest goodsRequest) {
		GoodsInfo goodsInfo = goodsRequest.toEntity();

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
		GoodsCategory goodsCategory = goodsCategoryRepository.findByName(name).orElse(null);
		if (goodsCategory == null) {
			goodsCategory =
				GoodsCategory
					.builder()
					.name(name)
					.build();
		}
		return goodsCategoryRepository.save(goodsCategory);
	}

}
