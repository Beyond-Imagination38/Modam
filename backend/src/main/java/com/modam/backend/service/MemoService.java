/*

//추후에 MemoRepository와 함께 추가

package com.modam.backend.service;

import com.modam.backend.dto.MemoDto;
import com.modam.backend.model.BookClub;
import com.modam.backend.model.Memo;
import com.modam.backend.repository.BookClubRepository;
import com.modam.backend.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memo_repository;
    private final BookClubRepository book_club_repository;

    public List<MemoDto> getMemosByClubId(int club_id) {
        return memo_repository.findByBookClub_ClubId(club_id).stream()
                .map(memo -> new MemoDto(
                        memo.getMemo_id(),
                        memo.getBookClub().getClub_id(),
                        memo.getUser_id(),
                        memo.getContent(),
                        memo.getCreated_time(),
                        memo.getUpdated_time()
                )).collect(Collectors.toList());
    }

    public MemoDto saveMemo(MemoDto dto) {
        BookClub book_club = book_club_repository.findById(dto.getClub_id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid club_id"));

        Memo memo = new Memo();
        memo.setBookClub(book_club);
        memo.setUser_id(dto.getUser_id());
        memo.setContent(dto.getContent());
        memo.setCreated_time(LocalDateTime.now());
        memo.setUpdated_time(LocalDateTime.now());

        Memo saved = memo_repository.save(memo);

        return new MemoDto(
                saved.getMemo_id(),
                saved.getBookClub().getClub_id(),
                saved.getUser_id(),
                saved.getContent(),
                saved.getCreated_time(),
                saved.getUpdated_time()
        );
    }
}

 */