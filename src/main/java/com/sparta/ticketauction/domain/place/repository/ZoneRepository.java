package com.sparta.ticketauction.domain.place.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.place.entity.Zone;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
}
