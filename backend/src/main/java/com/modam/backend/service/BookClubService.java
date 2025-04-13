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

    public BookClub getBookClub(int clubId) {
        return book_club_repository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("BookClub not found with id: " + clubId));
    }

    public List<BookClub> getBookclubsbybookid(int book_id) {
        return book_club_repository.findByBookId(book_id);
    }
}

