package com.simitchiyski.lockregistry.core.mapper;

import org.mapstruct.Mapping;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.List;

public interface DtoToEntityMapper<Dto, Entity extends AbstractPersistable> {

    @Mapping(ignore = true, target = "id")
    Entity toEntity(Dto dto);

    List<Entity> toEntity(List<Dto> dtoList);
}
