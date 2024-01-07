package com.sparta.ticketauction.domain.goods.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ImageType {
	POSTER_IMG("대표"),

	INFO_IMG("일반");

	private final String type;

	public static ImageType of(String type) {
		for (ImageType imageType : ImageType.values()) {
			if (imageType.type.equals(type)) {
				return imageType;
			}
		}
		throw new IllegalArgumentException("Invalid image type: " + type);

	}

}
