package com.sparta.ticketauction.domain.admin.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sparta.ticketauction.domain.admin.request.GoodsRequest;
import com.sparta.ticketauction.domain.admin.request.PlaceRequest;
import com.sparta.ticketauction.domain.admin.response.PlaceResponse;
import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.goods.entity.GoodsCategory;
import com.sparta.ticketauction.domain.goods.entity.GoodsImage;
import com.sparta.ticketauction.domain.goods.service.GoodsServiceImpl;
import com.sparta.ticketauction.domain.goods_sequence_seat.service.GoodsSequenceSeatServiceImpl;
import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.place.service.PlaceServiceImpl;
import com.sparta.ticketauction.domain.seat.entity.Seat;
import com.sparta.ticketauction.domain.seat.request.SeatRequest;
import com.sparta.ticketauction.domain.seat.service.SeatServiceImpl;
import com.sparta.ticketauction.domain.sequence.entity.Sequence;
import com.sparta.ticketauction.domain.sequence.service.SequenceServiceImpl;
import com.sparta.ticketauction.global.util.S3Uploader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final GoodsServiceImpl goodsService;

	private final PlaceServiceImpl placeService;

	private final SequenceServiceImpl sequenceService;

	private final SeatServiceImpl seatService;

	private final GoodsSequenceSeatServiceImpl goodsSequenceSeatService;

	private final S3Uploader s3Uploader;

	public static final String S3_PATH = "https://auction-ticket.s3.ap-northeast-2.amazonaws.com/";

	public static final String FILE_PATH = "goods/"; // goods/  thumbnail/ + (String)goodsId

	public static final String THUMBNAIL = "thumbnail/";

	public static final String GENERAL = "general/";

	// 공연장 생성
	@Override
	@Transactional
	public List<PlaceResponse> createPlace(PlaceRequest placeRequest) {
		List<SeatRequest> seats = placeRequest.getSeats();
		Integer totalSeat = totalCountSeat(seats);

		Place place = placeRequest.toEntity(totalSeat);
		Place savePlace = placeService.savePlace(place);

		List<Seat> seatList = createSeat(seats, savePlace);
		seatService.saveAllSeat(seatList);

		List<PlaceResponse> placeResponseList = new ArrayList<>();
		for (SeatRequest seat : seats) {
			placeResponseList.add(new PlaceResponse(seat.getZone(), seat.getZoneCountSeat(), place));
		}
		return placeResponseList;
	}

	// 공연 및 회차 생성
	@Override
	@Transactional
	public void createGoodsAndSequence(GoodsRequest goodsRequest, Long placeId, List<MultipartFile> files) {
		Place place = placeService.findPlace(placeId);

		Goods goods = goodsRequest.toEntity(place);
		Goods saveGoods = goodsService.saveGoods(goods);

		List<String> fileUrl = s3tUpload(files, saveGoods.getId());
		List<GoodsImage> goodsImageList = saveAllGoodsImage(fileUrl, saveGoods);
		saveGoods.addGoodsImage(goodsImageList);

		GoodsCategory goodsCategory = createGoodsCategory(goodsRequest.getCategoryName());
		saveGoods.updateGoodsCategory(goodsCategory);

		createSequence(saveGoods, goodsRequest.getStartTime());
	}

	// 총 좌석 개수 연산
	private Integer totalCountSeat(List<SeatRequest> seatRequests) {
		Integer totalSeat = 0;

		for (SeatRequest seat : seatRequests) {
			totalSeat += seat.getZoneCountSeat();
		}

		return totalSeat;
	}

	// 좌석 생성
	private List<Seat> createSeat(List<SeatRequest> seats, Place place) {
		return seats.stream()
			.flatMap(seat -> IntStream.rangeClosed(1, seat.getZoneCountSeat())
				.mapToObj(i -> seat.toEntity(place, i)))
			.collect(Collectors.toList());
	}

	// 이미지 저장
	private List<GoodsImage> saveAllGoodsImage(List<String> fileKeyList, Goods goods) {
		List<GoodsImage> goodsImageList = divideGoodsImageList(fileKeyList, goods);
		return goodsService.saveAllGoodsImage(goodsImageList);
	}

	// 이미지 종류 분리
	private List<GoodsImage> divideGoodsImageList(List<String> fileKeyList, Goods goods) {
		List<GoodsImage> returnGoodsIamgeList = new ArrayList<>();
		for (String fileKey : fileKeyList) {
			GoodsImage goodsImage =
				GoodsImage
					.builder()
					.s3Key(fileKey)
					.type(this.checkGoodsType(fileKey))
					.goods(goods)
					.build();
			returnGoodsIamgeList.add(goodsImage);
		}
		return returnGoodsIamgeList;
	}

	// 이미지 종류 체크
	private String checkGoodsType(String type) {
		if (type.contains(THUMBNAIL)) {
			return "대표";
		}
		return "일반";
	}

	// S3 저장
	private List<String> s3tUpload(List<MultipartFile> fileList, Long goodId) {
		List<String> fileUrl = new ArrayList<>();

		String thumbnailFilePath = FILE_PATH + THUMBNAIL + goodId;
		String generalFilePath = FILE_PATH + GENERAL + goodId;

		MultipartFile thumbnailMultipartFile = fileList.get(0);
		fileList.remove(0);

		String thumbnailUrl = s3Uploader.uploadSingleFileToS3(thumbnailMultipartFile, thumbnailFilePath);
		fileUrl.add(thumbnailUrl);

		List<String> generalUrl = s3Uploader.uploadFileToS3(fileList, generalFilePath);
		fileUrl.addAll(generalUrl);

		return fileUrl;
	}

	// 회차 생성
	private void createSequence(Goods goods, LocalTime startTime) {
		List<Sequence> saveSequenceList = distributeSequence(goods, startTime);
		saveSequence(saveSequenceList);
	}

	private List<Sequence> distributeSequence(Goods goods, LocalTime startTime) {
		LocalDate startDate = goods.getStartDate();
		LocalDate endDate = goods.getEndDate();

		List<Sequence> distributeSequenceList = new ArrayList<>();
		long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

		for (int i = 1; i <= daysBetween; i++) {
			LocalDateTime dateTime = startDate.atTime(startTime);
			Sequence sequence =
				Sequence
					.builder()
					.startDateTime(dateTime)
					.goods(goods)
					.sequence(i)
					.build();
			distributeSequenceList.add(sequence);
			startDate = startDate.plusDays(1);
		}

		return distributeSequenceList;
	}

	private void saveSequence(List<Sequence> sequenceList) {
		sequenceService.saveAllSequence(sequenceList);
	}

	// 카테고리 생성 기타 입력시
	private GoodsCategory createGoodsCategory(String category) {
		GoodsCategory goodsCategory = goodsService.findGoodsCategory(category);
		if (goodsCategory == null) {
			goodsCategory =
				GoodsCategory
					.builder()
					.name(category)
					.build();
		}
		return goodsService.saveGoodSCategory(goodsCategory);
	}

}
