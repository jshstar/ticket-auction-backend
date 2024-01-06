package com.sparta.ticketauction.domain.sequence.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;

import com.sparta.ticketauction.domain.goods.entity.Goods;
import com.sparta.ticketauction.domain.sequence.request.SequenceRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Sequence {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("회차 수")
	@Column(name = "sequence")
	private int sequence;

	@Comment("공연 일시")
	@Column(name = "start_date_time")
	private LocalDateTime startDateTime;

	@Comment("공연 id")
	@ManyToOne
	@JoinColumn(name = "goods_id")
	private Goods goods;

	public Sequence of(SequenceRequest sequenceRequest, Goods goods) {
		return new Sequence(sequenceRequest.getSequence(), sequenceRequest.getStartDateTime(), goods);
	}

	private Sequence(int sequence, LocalDateTime startDateTime, Goods goods) {
		this.sequence = sequence;
		this.startDateTime = startDateTime;
		this.goods = goods;
	}
}
