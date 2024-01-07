package com.sparta.ticketauction.domain.place.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.place.entity.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
