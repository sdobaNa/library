package com.example.library.repository;

import com.example.library.model.GeneralEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface GeneralRepository<T extends GeneralEntity> extends JpaRepository<T, UUID> {
    Page<T> findAllByNameContainingIgnoreCase(String name, Pageable paging);
    T findByUid(UUID uid);
}
