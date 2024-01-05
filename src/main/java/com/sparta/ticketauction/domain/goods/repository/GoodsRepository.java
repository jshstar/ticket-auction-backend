package com.sparta.ticketauction.domain.goods.repository;

import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;

import com.sparta.ticketauction.domain.goods.entity.Goods;

public interface GoodsRepository extends JpaAttributeConverter<Goods, Long> {
}
