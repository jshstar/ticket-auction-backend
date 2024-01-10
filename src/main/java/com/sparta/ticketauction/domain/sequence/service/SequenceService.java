package com.sparta.ticketauction.domain.sequence.service;

import java.util.List;

import com.sparta.ticketauction.domain.sequence.entity.Sequence;

public interface SequenceService {

	// 회차 리스트 저장
	void saveAllSequence(List<Sequence> sequenceList);

	// 회차 저장
	Sequence saveSequence(Sequence sequence);

	// 회차 탐색
	Sequence findSequence(Long sequenceId);
}
