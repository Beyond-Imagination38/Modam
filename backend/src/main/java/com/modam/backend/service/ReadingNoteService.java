package com.modam.backend.service;

//독후감 서비스

import com.modam.backend.dto.ReadingNoteDto;
import com.modam.backend.model.BookClub;
import com.modam.backend.model.ReadingNote;
import com.modam.backend.model.User;
import com.modam.backend.repository.BookClubRepository;
import com.modam.backend.repository.ReadingNoteRepository;
import com.modam.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadingNoteService {
    private final ReadingNoteRepository readingNoteRepository;
    private final UserRepository userRepository;
    private final BookClubRepository bookClubRepository;

    //독후감 저장 (처음 저장 또는 덮어쓰기)
    @Transactional
    public void saveNote(ReadingNoteDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        BookClub club = bookClubRepository.findById(dto.getClubId())
                .orElseThrow(() -> new RuntimeException("모임을 찾을 수 없습니다."));

        ReadingNote note = readingNoteRepository
                .findByBookClubClubIdAndUserUserId(dto.getClubId(), dto.getUserId())
                .orElse(new ReadingNote());

        note.setUser(user);
        note.setBookClub(club);
        note.setContent(dto.getContent());

        readingNoteRepository.save(note);
    }

    //독후감 불러오기 (유저+클럽 기준)
    @Transactional(readOnly = true)
    public ReadingNoteDto getNote(int clubId, int userId) {
        ReadingNote note = readingNoteRepository
                .findByBookClubClubIdAndUserUserId(clubId, userId)
                .orElseThrow(() -> new RuntimeException("해당 유저의 독후감이 없습니다."));

        return new ReadingNoteDto(userId, clubId, note.getContent());
    }

    //독후감 수정
    @Transactional
    public void updateNote(ReadingNoteDto dto) {
        ReadingNote note = readingNoteRepository
                .findByBookClubClubIdAndUserUserId(dto.getClubId(), dto.getUserId())
                .orElseThrow(() -> new RuntimeException("수정할 독후감이 존재하지 않습니다."));

        note.setContent(dto.getContent());
        readingNoteRepository.save(note);
    }

}
