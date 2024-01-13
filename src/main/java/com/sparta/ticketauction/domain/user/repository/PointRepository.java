package com.sparta.ticketauction.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.user.entity.Point;

public interface PointRepository extends JpaRepository<Point, Long> {
}
