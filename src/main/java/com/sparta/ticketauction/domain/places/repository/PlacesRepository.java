package com.sparta.ticketauction.domain.places.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.places.entity.Places;

public interface PlacesRepository extends JpaRepository<Places, Long> {
}
