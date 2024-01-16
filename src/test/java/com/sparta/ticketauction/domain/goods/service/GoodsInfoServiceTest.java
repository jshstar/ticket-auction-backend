package com.sparta.ticketauction.domain.goods.service;

import static com.sparta.ticketauction.domain.admin.service.AdminServiceImpl.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.util.ReflectionTestUtils;

import com.sparta.ticketauction.domain.goods.entity.GoodsCategory;
import com.sparta.ticketauction.domain.goods.entity.GoodsImage;
import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;
import com.sparta.ticketauction.domain.goods.repository.GoodsInfoRepository;
import com.sparta.ticketauction.domain.goods.response.GoodsInfoGetResponse;
import com.sparta.ticketauction.domain.goods.response.GoodsInfoGetSliceResponse;

@ExtendWith(MockitoExtension.class)
public class GoodsInfoServiceTest {

	@InjectMocks
	GoodsInfoServiceImpl goodsInfoService;

	@Mock
	GoodsInfoRepository goodsInfoRepository;

	List<GoodsInfo> goodsInfoList;

	@BeforeEach
	void init() {
		this.goodsInfoList = new ArrayList<>(List.of(GoodsInfo
				.builder()
				.name("공연1")
				.description("신나는 코드 작성 시간")
				.runningTime(360)
				.ageGrade(15)
				.build(),
			GoodsInfo
				.builder()
				.name("공연2")
				.description("새벽 코딩")
				.runningTime(360)
				.ageGrade(0)
				.build()
		)
		);

		List<String> fileUrl = new ArrayList<>();
		fileUrl.add("goods/thumbnail/1/51579925-f563-4c75-9999-e2264dadbdab");
		fileUrl.add("goods/general/1/0aebcd4f-b2b5-4bbc-b8f8-a10c4b8f2c17");
		fileUrl.add("goods/general/1/0a123123bcd4f-b325-4bbc-b8f8-a112452c17");
		fileUrl.add("goods/general/1/012312cd4f-b2b5-4bbc-b8f8-a1012534517");

		List<GoodsImage> goodsImageList1 = new ArrayList<>();
		goodsImageList1.add(
			GoodsImage.builder().s3Key(fileUrl.get(0)).type("대표").goodsInfo(this.goodsInfoList.get(0)).build());
		goodsImageList1.add(
			GoodsImage.builder().s3Key(fileUrl.get(1)).type("일반").goodsInfo(this.goodsInfoList.get(0)).build());

		List<GoodsImage> goodsImageList2 = new ArrayList<>();
		goodsImageList2.add(
			GoodsImage.builder().s3Key(fileUrl.get(2)).type("대표").goodsInfo(this.goodsInfoList.get(1)).build());
		goodsImageList2.add(
			GoodsImage.builder().s3Key(fileUrl.get(3)).type("대표").goodsInfo(this.goodsInfoList.get(1)).build());

		this.goodsInfoList.get(0).addGoodsImage(goodsImageList1);
		GoodsCategory goodsCategory1 = GoodsCategory.builder().name("연극").build();
		this.goodsInfoList.get(0).updateGoodsCategory(goodsCategory1);
		ReflectionTestUtils.setField(this.goodsInfoList.get(0), "id", 1L);

		this.goodsInfoList.get(1).addGoodsImage(goodsImageList2);
		GoodsCategory goodsCategory2 = GoodsCategory.builder().name("뮤지컬").build();
		this.goodsInfoList.get(1).updateGoodsCategory(goodsCategory2);
		ReflectionTestUtils.setField(this.goodsInfoList.get(1), "id", 2L);

	}

	@Test
	void 공연_정보_단건_조회_테스트() {

		// when
		given(goodsInfoRepository.findById(anyLong())).willReturn(Optional.of(this.goodsInfoList.get(0)));
		GoodsInfoGetResponse goodsInfoGetResponse = goodsInfoService.getGoodsInfo(1L);

		// then
		verify(goodsInfoRepository, times(1)).findById(anyLong());
		assertEquals(goodsInfoGetResponse.getName(), this.goodsInfoList.get(0).getName());
		assertEquals(goodsInfoGetResponse.getDescription(), this.goodsInfoList.get(0).getDescription());
		assertEquals(goodsInfoGetResponse.getRunningTime(), this.goodsInfoList.get(0).getRunningTime());
		assertEquals(goodsInfoGetResponse.getAgeGrade(), this.goodsInfoList.get(0).getAgeGrade().getKorea());
		assertEquals(
			goodsInfoGetResponse.getGoodsImages().get(0).getS3Url(),
			S3_PATH + this.goodsInfoList.get(0).getGoodsImage().get(0).getS3Key()
		);
		assertEquals(
			goodsInfoGetResponse.getGoodsImages().get(1).getS3Url(),
			S3_PATH + this.goodsInfoList.get(0).getGoodsImage().get(1).getS3Key()
		);
		assertEquals(
			goodsInfoGetResponse.getGoodsImages().get(0).getImageType(),
			this.goodsInfoList.get(0).getGoodsImage().get(0).getType().getType()
		);
		assertEquals(
			goodsInfoGetResponse.getGoodsImages().get(1).getImageType(),
			this.goodsInfoList.get(0).getGoodsImage().get(1).getType().getType()
		);
	}

	@Test
	void 공연_정보_페이징_조회_테스트() {
		// given
		int page = 0;
		int size = 2;
		Pageable pageable = PageRequest.of(page, size);

		List<GoodsInfo> filteredGoodsInfo = this.goodsInfoList.stream()
			.filter(goodsInfo -> goodsInfo.getGoodsCategory().getName().equals("연극"))
			.toList();

		int start = (int)pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), filteredGoodsInfo.size());
		List<GoodsInfo> pageContent = filteredGoodsInfo.subList(start, end);

		Slice<GoodsInfo> goodsInfoSlice = new PageImpl<>(pageContent, pageable, filteredGoodsInfo.size());

		// when
		given(
			goodsInfoRepository.findAllByCategoryName(any(Pageable.class), any(String.class)))
			.willReturn(goodsInfoSlice);

		GoodsInfoGetSliceResponse goodsInfoGetSliceResponse =
			goodsInfoService.getSliceGoodsInfo(pageable, "연극");

		//then
		verify(
			goodsInfoRepository, times(1))
			.findAllByCategoryName(any(Pageable.class), any(String.class));
		assertNotEquals(
			goodsInfoGetSliceResponse.getGoodsInfoSlice().getNumberOfElements(),
			goodsInfoList.size());
		assertEquals(
			goodsInfoGetSliceResponse.getGoodsInfoSlice().getContent().get(0).getName(),
			goodsInfoList.get(0).getName());
		assertEquals(
			goodsInfoGetSliceResponse.getGoodsInfoSlice().getContent().get(0).getS3Url(),
			S3_PATH + goodsInfoList.get(0).getGoodsImage().get(0).getS3Key());
	}

}
