package com.sparta.ticketauction.domain.grade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.grade.entity.ZoneGrade;

public interface ZoneGradeRepository extends JpaRepository<ZoneGrade, Long>, ZoneGradeQueryRepository {

}
