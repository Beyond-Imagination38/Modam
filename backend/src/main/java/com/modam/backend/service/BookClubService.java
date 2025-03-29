package com.modam.backend.service;

import com.modam.backend.model.BookClub;
import com.modam.backend.repository.BookClubRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookClubService {

    private final BookClubRepository book_club_repository;

    public BookClubService(BookClubRepository book_club_repository) {
        this.book_club_repository = book_club_repository;
    }

    public List<BookClub> getBookclubsbybookid(String book_id) {
        return book_club_repository.findByBookId(book_id);
    }
}

