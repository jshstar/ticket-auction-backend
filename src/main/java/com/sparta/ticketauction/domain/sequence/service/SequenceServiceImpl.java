package com.sparta.ticketauction.domain.sequence.service;

import static com.sparta.ticketauction.global.exception.ErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sparta.ticketauction.domain.sequence.entity.Sequence;
import com.sparta.ticketauction.domain.sequence.repository.SequenceRepository;
import com.sparta.ticketauction.global.exception.ApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SequenceServiceImpl implements SequenceService {

	private final SequenceRepository sequenceRepository;

	// 회차 리스트 저장
	@Override
	public void saveAllSequence(List<Sequence> sequenceList) {
		sequenceRepository.saveAll(sequenceList);
	}

	// 회차 저장
	@Override
	public Sequence saveSequence(Sequence sequence) {
		return sequenceRepository.save(sequence);
	}

	// 회차 탐색
	@Override
	public Sequence findSequence(Long sequenceId) {
		return sequenceRepository.findById(sequenceId)
			.orElseThrow(() -> new ApiException(NOT_FOUND_SEQUENCE));
	}

}
