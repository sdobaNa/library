package com.example.library.repository.library;

import com.example.library.model.library.Category;
import com.example.library.repository.GeneralRepository;

import java.util.UUID;

public interface CategoryRepository extends GeneralRepository<Category> {
    @Override
    Category findByUid(UUID uid);
}
