package com.kagzz.jmix.rys.app.repo;

public interface EntityRepository<DTO, Entity> {
    Entity save(DTO dto);
}