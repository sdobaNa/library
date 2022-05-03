package com.example.library.controller;

import com.example.library.model.GeneralEntity;
import com.example.library.service.GeneralService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class AbstractController<T extends GeneralEntity, S extends GeneralService<T>> implements GeneralController<T> {

    private final S service;

    public AbstractController(S service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<T> save(T entity) {
        try {
            return ResponseEntity.ok((T) service.saveOne(entity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<T> update(String uid, T entity) {
        try {
            return ResponseEntity.ok((T) service.updateOne(uid, entity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<List<T>> getAll(String name, int page, int size) {
        Pageable paging = PageRequest.of(page - 1, size);
        return ResponseEntity.ok(service.getAll(paging, name));
    }

    @Override
    public ResponseEntity<T> get(String uid) {
        return ResponseEntity.ok((T) service.getOne(uid));
    }

    @Override
    public ResponseEntity<Void> delete(String uid) {
        try {
            service.deleteOne(uid);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(204).build();
    }
}
