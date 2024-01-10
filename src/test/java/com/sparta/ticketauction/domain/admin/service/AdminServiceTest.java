package com.sparta.ticketauction.domain.admin.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sparta.ticketauction.domain.admin.request.GoodsRequest;
import com.sparta.ticketauction.domain.admin.request.PlaceRequest;
import com.sparta.ticketauction.domain.admin.response.PlaceResponse;
import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.goods.entity.GoodsCategory;
import com.sparta.ticketauction.domain.goods.entity.GoodsImage;
import com.sparta.ticketauction.domain.goods.service.GoodsServiceImpl;
import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.place.service.PlaceServiceImpl;
import com.sparta.ticketauction.domain.seat.request.SeatRequest;
import com.sparta.ticketauction.domain.seat.service.SeatServiceImpl;
import com.sparta.ticketauction.domain.sequence.entity.Sequence;
import com.sparta.ticketauction.domain.sequence.service.SequenceServiceImpl;
import com.sparta.ticketauction.global.util.S3Uploader;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

	@InjectMocks
	AdminServiceImpl adminService;

	@Mock
	PlaceServiceImpl placeService;

	@Mock
	SeatServiceImpl seatService;

	@Mock
	GoodsServiceImpl goodsService;

	@Mock
	S3Uploader s3Uploader;

	@Mock
	SequenceServiceImpl sequenceService;

	public static PlaceRequest placeRequest;

	public static GoodsRequest goodsRequest;

	@BeforeEach
	public void initPlaceRequest() {
		List<SeatRequest> seatRequests = new ArrayList<>();
		seatRequests.add(new SeatRequest("A", 100));
		placeRequest = new PlaceRequest("공연장", "Address", seatRequests);
		goodsRequest = new GoodsRequest(
			"강아지 공연",
			"강아지 쇼케이스",
			LocalDate.of(2023, 3, 1),
			LocalDate.of(2023, 3, 2),
			12,
			LocalTime.of(15, 0),
			360,
			"공연"
		);
	}

	@Test
	void 공연장_생성_테스트() {
		// given
		Place place = placeRequest.toEntity(100);

		//when
		given(placeService.savePlace(any(Place.class))).willReturn(place);
		List<PlaceResponse> response = adminService.createPlace(placeRequest);

		//then
		assertEquals("A", response.get(0).getZone());
		assertEquals(100, response.get(0).getZoneCountSeat());

	}

	@Test
	void 공연_공연이미지_공연카테고리_회차_생성_테스트() {
		// given
		Place place = Mockito.mock();
		Goods goods = goodsRequest.toEntity(place);

		List<String> fileUrl = new ArrayList<>();
		fileUrl.add("goods/thumbnail/1/51579925-f563-4c75-9999-e2264dadbdab");
		fileUrl.add("goods/general/1/0aebcd4f-b2b5-4bbc-b8f8-a10c4b8f2c17");

		List<GoodsImage> goodsImage = new ArrayList<>();
		goodsImage.add(GoodsImage.builder().s3Key(fileUrl.get(0)).type("대표").goods(goods).build());
		goodsImage.add(GoodsImage.builder().s3Key(fileUrl.get(1)).type("일반").goods(goods).build());

		goods.addGoodsImage(goodsImage);
		GoodsCategory goodsCategory = GoodsCategory.builder().name("공연").build();
		goods.updateGoodsCategory(goodsCategory);

		List<Sequence> sequenceList = new ArrayList<>();
		sequenceList.add(
			Sequence
				.builder()
				.sequence(1)
				.startDateTime(
					LocalDateTime.of(2023, 3, 1, 15, 0, 0))
				.goods(goods)
				.build()
		);
		sequenceList.add(
			Sequence
				.builder()
				.sequence(2)
				.startDateTime(
					LocalDateTime.of(2023, 3, 2, 15, 0, 0))
				.goods(goods)
				.build()
		);

		// when
		given(placeService.findPlace(1L)).willReturn(place);
		given(goodsService.saveGoods(any(Goods.class))).willReturn(goods);
		given(s3Uploader.uploadFileToS3(any(), any())).willReturn(Collections.singletonList(fileUrl.get(0)));
		given(s3Uploader.uploadSingleFileToS3(any(), any())).willReturn(fileUrl.get(1));
		given(goodsService.saveGoodSCategory(any())).willReturn(goodsCategory);
		adminService.createGoodsAndSequence(goodsRequest, 1L, mock());

		// then
		verify(placeService, times(1)).findPlace(anyLong());
		verify(goodsService, times(1)).saveGoods(any(Goods.class));
		assertEquals(goodsImage.get(0).getS3Key(), goods.getGoodsImage().get(0).getS3Key());
		assertEquals(goodsImage.get(1).getS3Key(), goods.getGoodsImage().get(1).getS3Key());
		assertEquals(goodsImage.get(0).getType(), goods.getGoodsImage().get(0).getType());
		assertEquals(goodsImage.get(1).getType(), goods.getGoodsImage().get(1).getType());
		assertEquals(goodsCategory.getName(), goods.getGoodsCategory().getName());
		assertEquals(sequenceList.get(0).getGoods().getName(), goods.getName());
		assertEquals(sequenceList.get(1).getGoods().getName(), goods.getName());
		assertEquals(sequenceList.get(1).getStartDateTime().getHour(), goodsRequest.getStartTime().getHour());
		assertEquals(sequenceList.get(1).getStartDateTime().getMinute(), goodsRequest.getStartTime().getMinute());
	}
}
