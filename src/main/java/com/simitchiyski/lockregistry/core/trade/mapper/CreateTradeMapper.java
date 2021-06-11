package com.simitchiyski.lockregistry.core.trade.mapper;

import com.simitchiyski.lockregistry.core.mapper.DtoToEntityMapper;
import com.simitchiyski.lockregistry.core.trade.Trade;
import com.simitchiyski.lockregistry.web.trade.dto.CreateTradeDTO;
import org.mapstruct.*;

import java.time.ZoneOffset;

@Mapper(componentModel = "spring", imports = {ZoneOffset.class}, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CreateTradeMapper extends DtoToEntityMapper<CreateTradeDTO, Trade> {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "cutoffDateTime", expression = "java(dto.getCutoffDateTime().withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime())")
    })
    Trade toEntity(CreateTradeDTO dto);
}
