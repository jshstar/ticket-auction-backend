package com.sparta.ticketauction.domain.places.entity;

import org.hibernate.annotations.Comment;

import com.sparta.ticketauction.domain.admin.request.PlacesRequest;
import com.sparta.ticketauction.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "places")
public class Places extends BaseEntity {
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

	public static Places of(PlacesRequest placesRequest) {
		return new Places(placesRequest.getName(), placesRequest.getAddress(), placesRequest.getCountSeats());
	}

	private Places(String name, String address, int countSeats) {
		this.name = name;
		this.address = address;
		this.countSeats = countSeats;
	}
}
