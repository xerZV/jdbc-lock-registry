package com.simitchiyski.lockregistry.core.mapper;

import org.springframework.data.jpa.domain.AbstractPersistable;

public interface TwoWayMapper<Dto, Entity extends AbstractPersistable> extends EntityToDtoMapper<Entity, Dto>,
        DtoToEntityMapper<Dto, Entity> {
}
