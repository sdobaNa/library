package com.example.library.controller.library;

import com.example.library.controller.AbstractController;
import com.example.library.model.library.Category;
import com.example.library.service.library.CategoryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController extends AbstractController<Category, CategoryService> {

    public CategoryController(CategoryService service) {
        super(service);
    }
}
