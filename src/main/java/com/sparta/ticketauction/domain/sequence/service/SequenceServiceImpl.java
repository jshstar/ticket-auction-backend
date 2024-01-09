package com.sparta.ticketauction.domain.sequence.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sparta.ticketauction.domain.sequence.entity.Sequence;
import com.sparta.ticketauction.domain.sequence.repository.SequenceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SequenceServiceImpl implements SequenceService {

	private final SequenceRepository sequenceRepository;

	public void saveAllSequence(List<Sequence> sequenceList) {
		sequenceRepository.saveAll(sequenceList);
	}

	public Sequence saveSequence(Sequence sequence) {
		return sequenceRepository.save(sequence);
	}

}
