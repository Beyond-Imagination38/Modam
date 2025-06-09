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
    private final ParticipantRepository participantRepository;


    public BookClubService
            (BookClubRepository bookClubRepository, BookRepository bookRepository,
             UserRepository userRepository, ParticipantRepository participantRepository) {
        this.bookClubRepository = bookClubRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.participantRepository = participantRepository;

    }

    // 모임 개설: participants 테이블 반영하도록 수정
    public BookClub createBookClub(BookClubCreateDto dto) {
        Book book = bookRepository.findByBookTitle(dto.getBookTitle())
                .orElseThrow(() -> new RuntimeException("해당 제목의 책이 존재하지 않습니다: " + dto.getBookTitle()));

        User host = userRepository.findById(dto.getHostId())
                .orElseThrow(() -> new RuntimeException("해당 사용자 ID가 존재하지 않습니다: " + dto.getHostId()));

        LocalDateTime meetingDateTime = LocalDateTime.of(dto.getDate(), dto.getTime());

        BookClub club = new BookClub();
        club.setHostId(dto.getHostId());
        club.setBookId(book.getBookId());
        club.setMeetingDate(meetingDateTime);
        club.setClubDescription(dto.getClubDescription());
        club.setStatus("PENDING");

        // 먼저 BookClub 저장
        BookClub savedClub = bookClubRepository.save(club);

        // host를 CONFIRMED 상태로 참가자 등록
        Participant participant = new Participant();
        participant.setBookClub(savedClub);
        participant.setUser(host);
        participant.setStatus("CONFIRMED");
        participantRepository.save(participant);

        return savedClub;
    }

    //메인 1. 모임 전체 조회(검색/정렬/필터)
    public List<BookClubCommonDto> searchBookClubs(BookClubSearchCondition condition) {
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
                .map(this::toCommonDto)  // 핵심: BookClub → BookClubCommonDto로 매핑
                .collect(Collectors.toList());
    }


    // 메인 2. 진행 중인 내 모임 조회: status=ongoing
    public List<BookClubCommonDto> getOngoingClubsByUserId(int userId) {
        List<Participant> participations = participantRepository.findWithBookClubByUserUserId(userId);

        return participations.stream()
                .map(Participant::getBookClub)
                .filter(club -> "ONGOING".equals(club.getStatus()))
                .map(this::toCommonDto)
                .collect(Collectors.toList());
    }


    public BookClub getBookClub(int clubId) {
        return bookClubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("BookClub not found with id: " + clubId));
    }

/*    public List<BookClub> getBookclubsbybookid(int book_id) {
        return bookClubRepository.findByBookId(book_id);
    }*/

    //메인 3.완료된 모임 조회: BookClub 상태가 COMPLETED이고 해당 userId가 참여한 경우
    public List<BookClubCommonDto> getCompletedClubsByUserId(int userId) {
        List<Participant> participations = participantRepository.findWithBookClubByUserUserId(userId);

        return participations.stream()
                .map(Participant::getBookClub)
                .filter(club -> "COMPLETED".equals(club.getStatus()))
                .map(this::toCommonDto)
                .collect(Collectors.toList());
    }

    //메인&상세 공통 모임 조회 dto
    private BookClubCommonDto toCommonDto(BookClub club) {
        int confirmedCount = participantRepository.countByBookClubClubIdAndStatus(club.getClubId(), "CONFIRMED");
        String participantDisplay = confirmedCount + "/4";
        return new BookClubCommonDto(
                club.getClubId(),
                club.getBook().getCoverImage(),
                club.getBook().getBookTitle(),
                club.getMeetingDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                club.getClubDescription(),
                participantDisplay
        );
    }

    // 상세 1. 상태 판단 로직
    public BookClubStatusDto getBookClubStatus(int clubId, int userId) {
        BookClub club = bookClubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("BookClub not found: " + clubId));

        int confirmedCount = participantRepository.countByBookClubClubIdAndStatus(clubId, "CONFIRMED");
        boolean isParticipant = participantRepository.existsByUserUserIdAndBookClubClubId(userId, clubId);

        String resultStatus;

        if (isParticipant && "COMPLETED".equals(club.getStatus())) {
            resultStatus = "COMPLETED";
        } else if (isParticipant && "ONGOING".equals(club.getStatus())) {
            resultStatus = "ONGOING";
        } else if (!isParticipant && confirmedCount >= 4) {
            resultStatus = "CLOSED";
        } else {
            resultStatus = "OPEN";
        }

        return new BookClubStatusDto(clubId, userId, resultStatus);
    }

    //상세 1. 모임 내용 반환
    public BookClubDetailDto getBookClubDetail(int clubId, int userId) {
        BookClub club = bookClubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("BookClub not found"));

        boolean isParticipating = participantRepository.existsByUserUserIdAndBookClubClubId(userId, clubId);
        int currentCount = participantRepository.countByBookClubClubIdAndStatus(clubId, "CONFIRMED");

        String status;
        if (isParticipating && "COMPLETED".equals(club.getStatus())) {
            status = "COMPLETED";
        } else if (isParticipating && "ONGOING".equals(club.getStatus())) {
            status = "ONGOING";
        } else if (!isParticipating && currentCount >= 4) {
            status = "CLOSED";
        } else {
            status = "OPEN";
        }

        return new BookClubDetailDto(
                club.getClubId(),
                club.getBook().getBookTitle(), // title
                club.getBook().getBookTitle(), // bookTitle
                club.getClubDescription(),
                club.getBook().getCoverImage(),
                club.getMeetingDate(),
                currentCount,
                4,
                isParticipating,
                status
        );
    }

    //상세 2. 모임 신청 API (/join)
    public void joinBookClub(int clubId, int userId) {
        // 1. 모임 상태 확인
        BookClubStatusDto statusDto = getBookClubStatus(clubId, userId);
        if (!"OPEN".equals(statusDto.getStatus())) {
            throw new RuntimeException("신청 가능한 상태가 아닙니다.");
        }

        // 2. 중복 신청 여부 확인
        if (participantRepository.existsByUserUserIdAndBookClubClubId(userId, clubId)) {
            throw new RuntimeException("이미 신청한 사용자입니다.");
        }

        // 3. 모임 및 사용자 조회
        BookClub club = bookClubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("해당 모임이 존재하지 않습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));

        // 4. 참가자 추가
        Participant participant = new Participant();
        participant.setBookClub(club);
        participant.setUser(user);
        participant.setStatus("CONFIRMED");
        participantRepository.save(participant);
    }





/*    // 모임 종료 시 요약 저장 및 상태 변경
    public void updateSummaryAndComplete(int clubId, String meetingSummary) {
        BookClub club = bookClubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("BookClub not found: " + clubId));
        club.setMeetingSummary(meetingSummary);
        club.setStatus("COMPLETED");
        bookClubRepository.save(club);
    }*/
    //아래를 삭제하고 위를 사용하는 것 나중에 테스트 해보기!!

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

