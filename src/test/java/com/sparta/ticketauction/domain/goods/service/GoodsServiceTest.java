package com.sparta.ticketauction.domain.goods.service;

import static com.sparta.ticketauction.domain.admin.service.AdminServiceImpl.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.goods.entity.GoodsCategory;
import com.sparta.ticketauction.domain.goods.entity.GoodsImage;
import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;
import com.sparta.ticketauction.domain.goods.repository.GoodsInfoRepository;
import com.sparta.ticketauction.domain.goods.repository.GoodsRepository;
import com.sparta.ticketauction.domain.goods.response.GoodsGetCursorResponse;
import com.sparta.ticketauction.domain.goods.response.GoodsGetQueryResponse;
import com.sparta.ticketauction.domain.goods.response.GoodsGetResponse;
import com.sparta.ticketauction.domain.goods.response.GoodsInfoGetResponse;
import com.sparta.ticketauction.domain.place.entity.Place;

@ExtendWith(MockitoExtension.class)
public class GoodsServiceTest {

	@InjectMocks
	GoodsServiceImpl goodsService;

	@Mock
	GoodsRepository goodsRepository;

	@Mock
	GoodsInfoRepository goodsInfoRepository;

	List<GoodsInfo> goodsInfoList;

	List<Goods> goodsList;

	List<Place> placeList;

	@BeforeEach
	void init() {
		// given
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

		ReflectionTestUtils.setField(this.goodsInfoList.get(0), "id", 1L);
		ReflectionTestUtils.setField(this.goodsInfoList.get(1), "id", 2L);

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

		this.placeList = new ArrayList<>(List.of(Place
				.builder()
				.name("공연장1")
				.address("주소1")
				.countSeats(100)
				.build(),
			Place
				.builder()
				.name("공연장2")
				.address("주소2")
				.countSeats(100)
				.build()
		)
		);
		ReflectionTestUtils.setField(this.placeList.get(0), "id", 1L);
		ReflectionTestUtils.setField(this.placeList.get(1), "id", 2L);

		this.goodsList = new ArrayList<>(
			List.of(
				Goods
					.builder()
					.title("공연1 - 서울")
					.endDate(LocalDate.of(2024, 2, 7))
					.startDate(LocalDate.of(2024, 2, 8))
					.goodsInfo(this.goodsInfoList.get(0))
					.place(this.placeList.get(0))
					.build(),
				Goods
					.builder()
					.title("공연2 - 부산")
					.endDate(LocalDate.of(2024, 2, 7))
					.startDate(LocalDate.of(2024, 2, 8))
					.goodsInfo(this.goodsInfoList.get(0))
					.place(this.placeList.get(0))
					.build())
		);

		ReflectionTestUtils.setField(this.goodsList.get(0), "id", 1L);
		ReflectionTestUtils.setField(this.goodsList.get(1), "id", 2L);

	}

	@Test
	void 공연_정보_전체_조회_테스트() {
		// when
		given(goodsInfoRepository.findAll()).willReturn(this.goodsInfoList);
		List<GoodsInfoGetResponse> goodsInfoGetResponseList = goodsService.getAllGoodsInfo();

		//then
		assertEquals(goodsInfoGetResponseList.get(0).getName(), this.goodsInfoList.get(0).getName());
		assertEquals(
			goodsInfoGetResponseList.get(0).getS3Url(),
			S3_PATH + this.goodsInfoList.get(0).getGoodsImage().get(0).getS3Key());
		assertEquals(goodsInfoGetResponseList.get(0).getGoodsInfoId(), this.goodsInfoList.get(0).getId());

	}

	@Test
	void 공연_단건_조회_테스트() {

		//when
		given(goodsRepository.findById(anyLong())).willReturn(Optional.ofNullable(this.goodsList.get(0)));
		GoodsGetResponse goodsGetResponse = goodsService.getGoods(1L);

		verify(goodsRepository, times(1)).findById(anyLong());
		assertEquals(goodsGetResponse.getGoodsId(), goodsList.get(0).getId());
		assertEquals(goodsGetResponse.getTitle(), goodsList.get(0).getTitle());
		assertEquals(goodsGetResponse.getStartDate(), goodsList.get(0).getStartDate());
		assertEquals(goodsGetResponse.getEndDate(), goodsList.get(0).getEndDate());
		assertEquals(goodsGetResponse.getRunningTime(), goodsList.get(0).getGoodsInfo().getRunningTime());
		assertEquals(goodsGetResponse.getAgeGrade(), goodsList.get(0).getGoodsInfo().getAgeGrade().getKorea());
		assertEquals(goodsGetResponse.getPlaceId(), goodsList.get(0).getPlace().getId());
		assertEquals(goodsGetResponse.getPlaceName(), goodsList.get(0).getPlace().getName());
		assertEquals(goodsGetResponse.getPlaceAddress(), goodsList.get(0).getPlace().getAddress());
		assertEquals(
			goodsGetResponse.getS3Urls().get(0),
			S3_PATH + goodsList.get(0).getGoodsInfo().getGoodsImage().get(0).getS3Key());

	}

	@Test
	void 공연_커서_전체_조회_테스트() {
		//given
		Long cursorId = 0L;
		int size = 2;
		String categoryName = "연극";
		List<GoodsGetQueryResponse> goodsGetQueryResponses = new ArrayList<>();
		goodsGetQueryResponses.add(
			new GoodsGetQueryResponse(
				goodsList.get(1).getId(),
				goodsList.get(1).getTitle(),
				goodsList.get(1).getGoodsInfo().getGoodsImage().get(1).getS3Key())
		);
		goodsGetQueryResponses.add(
			new GoodsGetQueryResponse(
				goodsList.get(0).getId(),
				goodsList.get(0).getTitle(),
				goodsList.get(0).getGoodsInfo().getGoodsImage().get(0).getS3Key())
		);
		// when
		given(goodsRepository.findAllByGoodsAndCategoryName(
			cursorId,
			size,
			categoryName
		))
			.willReturn(
				goodsGetQueryResponses
			);
		GoodsGetCursorResponse goodsGetCursorResponse = goodsService.getGoodsWithCursor(cursorId, size, categoryName);

		// then
		assertEquals(goodsGetCursorResponse.getNextCursorId(), 1);
		assertEquals(
			goodsGetCursorResponse.getGoodsResponses().get(0).getGoodsId(),
			goodsGetQueryResponses.get(0).getGoodsId()
		);
		assertEquals(
			goodsGetCursorResponse.getGoodsResponses().get(0).getTitle(),
			goodsGetQueryResponses.get(0).getTitle()
		);
		assertEquals(
			goodsGetCursorResponse.getGoodsResponses().get(0).getS3Url(),
			goodsGetQueryResponses.get(0).getS3Url()
		);
	}

}
