package com.sparta.ticketauction.domain.place.entity;

import org.hibernate.annotations.Comment;

import com.sparta.ticketauction.domain.admin.request.PlaceRequest;
import com.sparta.ticketauction.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "place")
public class Place extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("공연장 이름")
	@Column(name = "name", length = 30)
	private String name;

	@Comment("공연장 주소")
	@Column(name = "address", length = 150)
	private String address;

	@Comment("총 좌석 개수")
	@Column(name = "count_seats")
	private int countSeats;

	public static Place of(PlaceRequest placeRequest, int countSeats) {
		return new Place(placeRequest.getName(), placeRequest.getAddress(), countSeats);
	}

	private Place(String name, String address, int countSeats) {
		this.name = name;
		this.address = address;
		this.countSeats = countSeats;
	}
}