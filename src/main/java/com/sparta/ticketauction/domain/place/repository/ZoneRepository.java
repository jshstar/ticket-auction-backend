package com.sparta.ticketauction.domain.place.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.place.entity.Zone;

public interface ZoneRepository extends JpaRepository<Zone, Long> {

	// 해당 공연장에 있는 구역정보 전체 조회
	List<Zone> findAllByPlaceId(Long placeId);
}
