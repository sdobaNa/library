package com.example.library.service.library;

import com.example.library.model.library.Category;
import com.example.library.repository.library.CategoryRepository;
import com.example.library.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractService<Category, CategoryRepository> {
    public CategoryService(CategoryRepository repository) {
        super(repository);
    }
}
