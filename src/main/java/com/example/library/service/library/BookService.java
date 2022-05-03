package com.example.library.service.library;

import com.example.library.model.library.Book;
import com.example.library.repository.library.BookRepository;
import com.example.library.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class BookService extends AbstractService<Book, BookRepository> {

    public BookService(BookRepository repository) {
        super(repository);
    }
}
