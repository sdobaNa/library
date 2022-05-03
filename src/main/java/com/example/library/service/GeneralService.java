package com.example.library.service;

import com.example.library.model.GeneralEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GeneralService<T extends GeneralEntity> {
    T getOne(String uid);
    List<T> getAll(Pageable paging, String name);
    T saveOne(T entity) throws Exception;
    T updateOne(String uid, T entity) throws Exception;
    void deleteOne(String uid) throws Exception;
}
