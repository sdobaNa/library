package com.example.library.controller.library;

import com.example.library.controller.AbstractController;
import com.example.library.model.library.Book;
import com.example.library.service.library.BookService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/book")
public class BookController extends AbstractController<Book, BookService> {

    public BookController(BookService service) {
        super(service);
    }
}
