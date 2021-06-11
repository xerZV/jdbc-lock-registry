package com.simitchiyski.lockregistry.core.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.List;

public interface EntityToDtoMapper<Entity extends AbstractPersistable, Dto> {

    Dto toDto(Entity entity);

    List<Dto> toDto(List<Entity> entityList);

    default Page<Dto> toDtoPage(Page<Entity> entityPage) {
        return new PageImpl<>(toDto(entityPage.getContent()), entityPage.getPageable(), entityPage.getTotalPages());
    }
}
