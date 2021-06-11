package com.simitchiyski.lockregistry.core.trade.mapper;

import com.simitchiyski.lockregistry.core.mapper.EntityToDtoMapper;
import com.simitchiyski.lockregistry.core.trade.Trade;
import com.simitchiyski.lockregistry.web.trade.dto.TradeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TradeMapper extends EntityToDtoMapper<Trade, TradeDTO> {
}
