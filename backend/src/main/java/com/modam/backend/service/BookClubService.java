package com.modam.backend.service;

import com.modam.backend.dto.*;
import com.modam.backend.model.*;
import com.modam.backend.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookClubService {

    private final BookClubRepository bookClubRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookClubService(BookClubRepository bookClubRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.bookClubRepository = bookClubRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;

    }

    // 모임 개설
    public BookClub createBookClub(BookClubCreateDto dto) {
        // 책 확인
        Book book = bookRepository.findByBookTitle(dto.getBookTitle())
                .orElseThrow(() -> new RuntimeException("해당 제목의 책이 존재하지 않습니다: " + dto.getBookTitle()));

        // 사용자 확인 (host 존재 확인용)
        User host = userRepository.findById(dto.getHostId())
                .orElseThrow(() -> new RuntimeException("해당 사용자 ID가 존재하지 않습니다: " + dto.getHostId()));

        LocalDateTime meetingDateTime = LocalDateTime.of(dto.getDate(), dto.getTime());

        BookClub club = new BookClub();
        club.setHostId(dto.getHostId());
        club.setBookId(book.getBookId());
        club.setMeetingDate(meetingDateTime);
        club.setClubDescription(dto.getClubDescription());
        club.setStatus("PENDING");

        return bookClubRepository.save(club);
    }

    //모임 전체 조회(검색/정렬/필터)
    public List<BookClubSelectDto> searchBookClubs(BookClubSearchCondition condition) {
        List<BookClub> clubs = bookClubRepository.findAll();

        return clubs.stream()
                .filter(club -> {
                    boolean matchesKeyword = condition.getKeyword() == null ||
                            club.getBook().getBookTitle().toLowerCase().contains(condition.getKeyword().toLowerCase()) ||
                            (club.getClubDescription() != null &&
                                    club.getClubDescription().toLowerCase().contains(condition.getKeyword().toLowerCase()));

                    boolean matchesStatus = condition.getStatus() == null ||
                            club.getStatus().equalsIgnoreCase(condition.getStatus());

                    return matchesKeyword && matchesStatus;
                })
                .sorted((a, b) -> {
                    if (condition.getSortBy() == null || condition.getSortBy().equals("latest")) {
                        return b.getCreatedTime().compareTo(a.getCreatedTime());
                    } else if (condition.getSortBy().equals("meetingDate")) {
                        return a.getMeetingDate().compareTo(b.getMeetingDate());
                    }
                    return 0;
                })
                .map(club -> new BookClubSelectDto(
                        club.getClubId(),
                        club.getBook().getBookTitle(),
                        club.getMeetingDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                        club.getBook().getCoverImage(),
                        club.getStatus()
                ))
                .collect(Collectors.toList());
    }

    // 조회

    public BookClub getBookClub(int clubId) {
        return bookClubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("BookClub not found with id: " + clubId));
    }

    public List<BookClub> getBookclubsbybookid(int book_id) {
        return bookClubRepository.findByBookId(book_id);
    }






    //요약 전달
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

