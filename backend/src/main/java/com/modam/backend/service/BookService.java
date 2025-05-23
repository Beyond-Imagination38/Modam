package com.modam.backend.service;

import com.modam.backend.model.Book;
import com.modam.backend.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    private final BookRepository book_repository;

    public BookService(BookRepository book_repository) {
        this.book_repository = book_repository;
    }

    public Optional<Book> getBookById(Integer book_id) {
        return book_repository.findById(book_id);
    }
}
