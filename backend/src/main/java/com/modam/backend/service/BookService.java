package com.modam.backend.service;

import com.modam.backend.model.Book;
import com.modam.backend.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Optional<Book> getBookById(String bookId) {  // 기존 int → String 변경
        return bookRepository.findById(bookId);
    }
}
