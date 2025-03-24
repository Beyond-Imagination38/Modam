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
    private final MemoRepository memoRepository;
    private final BookClubRepository bookClubRepository;

    public List<MemoDto> getMemosByClubId(int clubId) {
        return memoRepository.findByBookClub_ClubId(clubId).stream()
                .map(memo -> new MemoDto(
                        memo.getMemoId(),
                        memo.getBookClub().getClubId(),
                        memo.getUserId(),
                        memo.getContent(),
                        memo.getCreatedTime(),
                        memo.getUpdatedTime()
                )).collect(Collectors.toList());
    }

    public MemoDto saveMemo(MemoDto dto) {
        BookClub bookClub = bookClubRepository.findById(dto.getClubId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid clubId"));

        Memo memo = new Memo();
        memo.setBookClub(bookClub);
        memo.setUserId(dto.getUserId());
        memo.setContent(dto.getContent());
        memo.setCreatedTime(LocalDateTime.now());  // 직접 지정해도 무방
        memo.setUpdatedTime(LocalDateTime.now());

        Memo saved = memoRepository.save(memo);

        return new MemoDto(
                saved.getMemoId(),
                saved.getBookClub().getClubId(),
                saved.getUserId(),
                saved.getContent(),
                saved.getCreatedTime(),
                saved.getUpdatedTime()
        );
    }
}

*/
