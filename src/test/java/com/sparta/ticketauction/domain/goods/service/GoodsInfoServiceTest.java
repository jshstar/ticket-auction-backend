package com.sparta.ticketauction.domain.goods.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.sparta.ticketauction.domain.goods.entity.GoodsCategory;
import com.sparta.ticketauction.domain.goods.entity.GoodsImage;
import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;
import com.sparta.ticketauction.domain.goods.repository.GoodsInfoRepository;
import com.sparta.ticketauction.domain.goods.response.GoodsInfoGetResponse;

@ExtendWith(MockitoExtension.class)
public class GoodsInfoServiceTest {

	@InjectMocks
	GoodsInfoServiceImpl goodsInfoService;

	@Mock
	GoodsInfoRepository goodsInfoRepository;

	@Test
	void 공연_정보_단건_조회_테스트() {
		// given
		GoodsInfo goodsInfo =
			GoodsInfo
				.builder()
				.name("공연1")
				.description("신나는 코드 작성 시간")
				.runningTime(360)
				.ageGrade(15)
				.build();

		List<String> fileUrl = new ArrayList<>();
		fileUrl.add("goods/thumbnail/1/51579925-f563-4c75-9999-e2264dadbdab");
		fileUrl.add("goods/general/1/0aebcd4f-b2b5-4bbc-b8f8-a10c4b8f2c17");

		List<GoodsImage> goodsImage = new ArrayList<>();
		goodsImage.add(GoodsImage.builder().s3Key(fileUrl.get(0)).type("대표").goodsInfo(goodsInfo).build());
		goodsImage.add(GoodsImage.builder().s3Key(fileUrl.get(1)).type("일반").goodsInfo(goodsInfo).build());

		goodsInfo.addGoodsImage(goodsImage);
		GoodsCategory goodsCategory = GoodsCategory.builder().name("공연").build();
		goodsInfo.updateGoodsCategory(goodsCategory);

		ReflectionTestUtils.setField(goodsInfo, "id", 1L);
		// when
		given(goodsInfoRepository.findById(anyLong())).willReturn(Optional.of(goodsInfo));
		GoodsInfoGetResponse goodsInfoGetResponse = goodsInfoService.getGoodsInfo(1L);

		// then
		verify(goodsInfoRepository, times(1)).findById(anyLong());
		assertEquals(goodsInfoGetResponse.getName(), goodsInfo.getName());
		assertEquals(goodsInfoGetResponse.getDescription(), goodsInfo.getDescription());
		assertEquals(goodsInfoGetResponse.getRunningTime(), goodsInfo.getRunningTime());
		assertEquals(goodsInfoGetResponse.getAgeGrade(), goodsInfo.getAgeGrade());
		assertEquals(goodsInfoGetResponse.getGoodsImages().get(0), goodsInfo.getGoodsImage().get(0));
		assertEquals(goodsInfoGetResponse.getGoodsImages().get(1), goodsInfo.getGoodsImage().get(1));
	}
}
