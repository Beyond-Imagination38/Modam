package com.modam.backend.service;

import com.modam.backend.dto.ClubListDto;
import com.modam.backend.model.Book;
import com.modam.backend.model.BookClub;
import com.modam.backend.repository.BookClubRepository;
import com.modam.backend.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookClubService {

    private final BookClubRepository bookClubRepository;
    private final BookRepository bookRepository;


    public BookClubService(BookClubRepository bookClubRepository, BookRepository bookRepository) {
        this.bookClubRepository = bookClubRepository;
        this.bookRepository = bookRepository;

    }

    public BookClub getBookClub(int clubId) {
        return bookClubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("BookClub not found with id: " + clubId));
    }

    public List<BookClub> getBookclubsbybookid(int book_id) {
        return bookClubRepository.findByBookId(book_id);
    }

    // 메인 페이지: ClubListDto 반환
    public List<ClubListDto> getAllClubSummaries() {
        List<BookClub> clubs = bookClubRepository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return clubs.stream().map(club -> {
            Book book = bookRepository.findById(club.getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not found with id: " + club.getBookId()));

            return new ClubListDto(
                    club.getClubId(),
                    club.getHostId(),
                    book.getTitle(),
                    club.getMeetingDate().format(formatter),
                    book.getCoverImage(),
                    convertStatusToCategory(club.getStatus())
            );
        }).collect(Collectors.toList());
    }

    // 예: status: "ONGOING" → category: "진행 중"
    private String convertStatusToCategory(String status) {
        return switch (status) {
            case "ONGOING" -> "진행 중";
            case "COMPLETED" -> "완료";
            case "PENDING" -> "대기 중";

            default -> "대기 중";
        };
    }

    // 모임 종료시 요약본 저장 및 모임 종료시 "completed"로 status 전환
    public void updateSummaryAndComplete(int clubId, String meetingSummary) {
        BookClub club = bookClubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("BookClub not found: " + clubId));

        club.setMeetingSummary(meetingSummary);  // 요약문 저장
        club.setStatus("COMPLETED");              // 상태 변경

        bookClubRepository.save(club);            // 한 번에 저장
    }




}

