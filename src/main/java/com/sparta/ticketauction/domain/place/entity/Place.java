package com.sparta.ticketauction.domain.place.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import com.sparta.ticketauction.global.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
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
	@Column(name = "name", length = 30, nullable = false)
	private String name;

	@Comment("공연장 주소")
	@Column(name = "address", length = 150, nullable = false)
	private String address;

	@Comment("총 좌석 개수")
	@Column(name = "count_seats", nullable = false)
	@ColumnDefault("0")
	private Integer countSeats = 0;

	@Comment("공연장 구역")
	@OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Zone> zones = new ArrayList<>();

	@Builder
	private Place(String name, String address, Integer countSeats) {
		this.name = name;
		this.address = address;
		this.countSeats = countSeats;
	}

	public void updateZone(List<Zone> zone) {
		this.zones.addAll(zone);
	}
}
