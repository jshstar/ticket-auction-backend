package com.sparta.ticketauction.domain.sequence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparta.ticketauction.domain.sequence.entity.Sequence;

public interface SequenceRepository extends JpaRepository<Sequence, Long> {

	@Query("select s from Sequence s "
		+ "join fetch s.goods g "
		+ "join fetch g.goodsImage i "
		+ "where s.id = :sequenceId and i.type = 'POSTER_IMG'")
	Optional<Sequence> findSequenceWithGoodsById(Long sequenceId);
}
