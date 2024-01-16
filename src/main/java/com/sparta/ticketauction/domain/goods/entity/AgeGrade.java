package com.sparta.ticketauction.domain.goods.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum AgeGrade {
	AGE_ALL(0, "전체 이용가"),

	AGE_12(12, "12세 이용가"),

	AGE_15(15, "15세 이용가"),

	AGE_19(19, "19세 이용가");

	private final int age;

	private final String korea;

	public static AgeGrade of(int age) {
		for (AgeGrade ageGrade : AgeGrade.values()) {
			if (ageGrade.age == age) {
				return ageGrade;
			}
		}
		throw new IllegalArgumentException("Invalid age grade: " + age);
	}

}
