package com.sparta.ticketauction.domain.sequence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.sequence.entity.Sequence;

public interface SequenceRepository extends JpaRepository<Sequence, Long> {
}
