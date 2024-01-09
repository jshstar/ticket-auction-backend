package com.sparta.ticketauction.domain.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.ticketauction.domain.admin.request.PlaceRequest;
import com.sparta.ticketauction.domain.admin.response.PlaceResponse;
import com.sparta.ticketauction.domain.goods.service.GoodsServiceImpl;
import com.sparta.ticketauction.domain.goods_sequence_seat.service.GoodsSequenceSeatServiceImpl;
import com.sparta.ticketauction.domain.place.entity.Place;
import com.sparta.ticketauction.domain.place.service.PlaceServiceImpl;
import com.sparta.ticketauction.domain.seat.entity.Seat;
import com.sparta.ticketauction.domain.seat.request.SeatRequest;
import com.sparta.ticketauction.domain.seat.service.SeatServiceImpl;
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
		List<Seat> seatList = new ArrayList<>();

		for (SeatRequest seat : seats) {
			for (int i = 1; i <= seat.getZoneCountSeat(); i++) {
				seatList.add(seat.toEntity(place, i));
			}
		}

		return seatList;
	}

}
