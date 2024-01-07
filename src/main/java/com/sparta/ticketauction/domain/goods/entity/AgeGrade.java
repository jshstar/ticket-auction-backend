package com.sparta.ticketauction.domain.goods.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum AgeGrade {
	AGE_ALL(0),

	AGE_12(12),

	AGE_15(15),

	AGE_19(19);

	private final int age;

	public static AgeGrade of(int age) {
		for (AgeGrade ageGrade : AgeGrade.values()) {
			if (ageGrade.age == age) {
				return ageGrade;
			}
		}
		throw new IllegalArgumentException("Invalid age grade: " + age);
	}

}
