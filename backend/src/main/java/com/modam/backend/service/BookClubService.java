package com.modam.backend.service;

import com.modam.backend.model.BookClub;
import com.modam.backend.repository.BookClubRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookClubService {
    private final BookClubRepository bookClubRepository;

    public BookClubService(BookClubRepository bookClubRepository) {
        this.bookClubRepository = bookClubRepository;
    }

    public List<BookClub> getBookClubsByBookId(String bookId) { // 기존 int → String 변경
        return bookClubRepository.findByBookId(bookId);
    }
}
