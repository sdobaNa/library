package com.example.library.repository.library;

import com.example.library.model.library.Book;
import com.example.library.repository.GeneralRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface BookRepository extends GeneralRepository<Book> {
    Page<Book> findAllByNameContainingIgnoreCase(String name, Pageable paging);

    @Override
    Book findByUid(UUID uid);
}
