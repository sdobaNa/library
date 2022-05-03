package com.example.library.service;

import com.example.library.model.GeneralEntity;
import com.example.library.repository.GeneralRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public abstract class AbstractService<T extends GeneralEntity, R extends GeneralRepository<T>> implements GeneralService<T> {

    protected final R repository;

    public AbstractService(R repository) {
        this.repository = repository;
    }

    @Override
    public T getOne(String uid) {
        return repository.findByUid(UUID.fromString(uid));
    }

    @Override
    public List<T> getAll(Pageable paging, String name) {
        return repository.findAllByNameContainingIgnoreCase(name, paging).toList();
    }

    @Override
    public T saveOne(T entity) throws Exception {
        return repository.save(entity);
    }

    @Override
    public T updateOne(String uid, T entity) throws Exception {
        GeneralEntity oldEntity = repository.findByUid(UUID.fromString(uid));
        if (oldEntity != null) {
            entity.setUid(uid);
            repository.save(entity);
        }
        return entity;
    }

    @Override
    public void deleteOne(String uid) throws Exception {
        repository.deleteById(UUID.fromString(uid));
    }
}
