package com.sparta.ticketauction.domain.admin.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sparta.ticketauction.domain.admin.request.GoodsRequest;
import com.sparta.ticketauction.domain.admin.request.GoodsSequenceSeatRequest;
import com.sparta.ticketauction.domain.admin.request.PlaceRequest;
import com.sparta.ticketauction.domain.admin.response.PlaceResponse;
import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.goods.entity.GoodsCategory;
import com.sparta.ticketauction.domain.goods.entity.GoodsImage;
import com.sparta.ticketauction.domain.goods.service.GoodsService;
import com.sparta.ticketauction.domain.goods_sequence_seat.entity.GoodsSequenceSeat;
import com.sparta.ticketauction.domain.goods_sequence_seat.entity.SellType;
import com.sparta.ticketauction.domain.goods_sequence_seat.service.GoodsSequenceSeatService;
import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.place.service.PlaceService;
import com.sparta.ticketauction.domain.seat.entity.Seat;
import com.sparta.ticketauction.domain.seat.request.SeatRequest;
import com.sparta.ticketauction.domain.seat.service.SeatService;
import com.sparta.ticketauction.domain.sequence.entity.Sequence;
import com.sparta.ticketauction.domain.sequence.service.SequenceService;
import com.sparta.ticketauction.global.util.S3Uploader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final GoodsService goodsService;

	private final PlaceService placeService;

	private final SequenceService sequenceService;

	private final SeatService seatService;

	private final GoodsSequenceSeatService goodsSequenceSeatService;

	private final S3Uploader s3Uploader;

	// private final AuctionService auctionService;

	public static final String S3_PATH = "https://auction-ticket.s3.ap-northeast-2.amazonaws.com/";

	public static final String FILE_PATH = "goods/";

	public static final String THUMBNAIL = "thumbnail/";

	public static final String GENERAL = "general/";

	// 공연장 생성
	@Override
	@Transactional
	public List<PlaceResponse> createPlace(PlaceRequest placeRequest) {
		// 좌석 카운트
		List<SeatRequest> seats = placeRequest.getSeats();
		Integer totalSeat = totalCountSeat(seats);

		// 공연 저장
		Place place = placeRequest.toEntity(totalSeat);
		Place savePlace = placeService.savePlace(place);

		// 좌석 저장
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

		// 공연 저장
		Goods goods = goodsRequest.toEntity(place);
		Goods saveGoods = goodsService.saveGoods(goods);

		// 이미지 저장
		List<String> fileUrl = s3tUpload(files, saveGoods.getId());
		List<GoodsImage> goodsImageList = saveAllGoodsImage(fileUrl, saveGoods);
		saveGoods.addGoodsImage(goodsImageList);

		// 카테고리 저장
		GoodsCategory goodsCategory = createGoodsCategory(goodsRequest.getCategoryName());
		saveGoods.updateGoodsCategory(goodsCategory);

		// 회차 저장
		createSequence(saveGoods, goodsRequest.getStartTime());
	}

	// 공연별 회차 생성 및 경매 생성
	@Override
	@Transactional
	public void createGoodsSequenceSeatAndAuction(
		Long placeId,
		Long sequenceId,
		GoodsSequenceSeatRequest goodsSequenceSeatRequest
	) {
		List<Seat> allSeatOfZone = seatService.findAllSeatOfZone(placeId, goodsSequenceSeatRequest.getZone());
		Sequence sequence = sequenceService.findSequence(sequenceId);
		List<GoodsSequenceSeat> goodsSequenceSeatList = checkAuctionAndGeneralSeat(allSeatOfZone, sequence,
			goodsSequenceSeatRequest);

		saveAllGoodsSequenceSeat(goodsSequenceSeatList);

		List<GoodsSequenceSeat> sequenceAuctionList =
			goodsSequenceSeatService.findAllBySequenceIdAndSellType(sequence.getId(), SellType.AUCTION);
		// createAuction(List<GoodsSequenceSeat> sequenceAuctionList);
	}

	// 옥션 공연 회차별 좌석 생성
	public List<GoodsSequenceSeat> checkAuctionAndGeneralSeat(
		List<Seat> allSeatOfZone,
		Sequence sequence,
		GoodsSequenceSeatRequest sequenceSeatRequest
	) {
		Set<Integer> auctionSeatNumberList = new HashSet<>(sequenceSeatRequest.getAuctionSeats());
		List<GoodsSequenceSeat> sequenceSeats = new ArrayList<>();

		for (Seat seat : allSeatOfZone) {
			GoodsSequenceSeat goodsSequenceSeat = sequenceSeatRequest.toEntity(seat, sequence);
			Long price = sequenceSeatRequest.getGeneralAuctionPrice();
			SellType sellType = SellType.NORMAL;

			if (auctionSeatNumberList.contains(seat.getSeatNumber())) {
				price = sequenceSeatRequest.getAuctionPrice();
				sellType = SellType.AUCTION;
			}

			updatePriceAndSellType(goodsSequenceSeat, price, sellType);
			sequenceSeats.add(goodsSequenceSeat);
		}

		return sequenceSeats;
	}

	// public GoodsSequenceSeat initSequenceSeat(
	// 	Seat seat,
	// 	Set<Integer> auctionSeatNumberList,
	// 	GoodsSequenceSeatRequest sequenceSeatRequest){
	//
	// 	Long price = sequenceSeatRequest.getGeneralAuctionPrice();
	// 	SellType sellType = SellType.NORMAL;
	//
	//
	// 	if (auctionSeatNumberList.contains(seat.getSeatNumber())) {
	// 		price = sequenceSeatRequest.getAuctionPrice();
	// 		sellType = SellType.AUCTION;
	// 	}
	//
	// 	// return
	// }

	public GoodsSequenceSeat updatePriceAndSellType(GoodsSequenceSeat goodsSequenceSeat, Long price,
		SellType sellType) {
		goodsSequenceSeat.updatePrice(price);
		goodsSequenceSeat.updateSellType(sellType);
		return goodsSequenceSeat;
	}

	// 일반 좌석 공연 회차별 좌석 생성

	// 모든 좌석 생성
	public void saveAllGoodsSequenceSeat(List<GoodsSequenceSeat> goodsSequenceSeatList) {
		goodsSequenceSeatService.saveAllGoodsSequenceSeat(goodsSequenceSeatList);
	}

	// 총 좌석 개수 연산
	public Integer totalCountSeat(List<SeatRequest> seatRequests) {
		Integer totalSeat = 0;

		for (SeatRequest seat : seatRequests) {
			totalSeat += seat.getZoneCountSeat();
		}

		return totalSeat;
	}

	// 좌석 생성
	public List<Seat> createSeat(List<SeatRequest> seats, Place place) {
		return seats.stream()
			.flatMap(seat -> IntStream.rangeClosed(1, seat.getZoneCountSeat())
				.mapToObj(i -> seat.toEntity(place, i)))
			.collect(Collectors.toList());
	}

	// 이미지 저장
	public List<GoodsImage> saveAllGoodsImage(List<String> fileKeyList, Goods goods) {
		List<GoodsImage> goodsImageList = divideGoodsImageList(fileKeyList, goods);
		return goodsService.saveAllGoodsImage(goodsImageList);
	}

	// 이미지 종류 분리
	public List<GoodsImage> divideGoodsImageList(List<String> fileKeyList, Goods goods) {
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
	public String checkGoodsType(String type) {
		if (type.contains(THUMBNAIL)) {
			return "대표";
		}
		return "일반";
	}

	// S3 저장
	public List<String> s3tUpload(List<MultipartFile> fileList, Long goodId) {
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
	public void createSequence(Goods goods, LocalTime startTime) {
		List<Sequence> saveSequenceList = distributeSequence(goods, startTime);
		saveSequence(saveSequenceList);
	}

	public void saveSequence(List<Sequence> sequenceList) {
		sequenceService.saveAllSequence(sequenceList);
	}

	// 회차 분리
	public List<Sequence> distributeSequence(Goods goods, LocalTime startTime) {
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

	// 카테고리 생성 기타 입력시
	public GoodsCategory createGoodsCategory(String category) {
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
