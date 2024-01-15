package com.sparta.ticketauction.domain.grade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.grade.entity.Grade;

public interface GradeRepository extends JpaRepository<Grade, Long> {
}
