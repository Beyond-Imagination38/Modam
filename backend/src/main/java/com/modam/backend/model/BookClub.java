package com.modam.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookclub")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookClub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Integer clubId;

    // hostId 원시값 필드
    @Column(name = "host_id", nullable = false)
    private Integer hostId;

    // host와 User 연관관계 (optional: fetch=LAZY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id", insertable = false, updatable = false)
    private User host;

    @Column(name = "book_id", nullable = false)
    private Integer bookId;

    // 연관관계 매핑 추가
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", insertable = false, updatable = false)
    private Book book;

    @Column(name = "meeting_date", nullable = false)
    private LocalDateTime meetingDate;

    @Column(name = "status", nullable = false, columnDefinition = "ENUM('PENDING', 'ONGOING', 'COMPLETED') DEFAULT 'PENDING'")
    private String status;

    //@Column(name = "likes", nullable = false, columnDefinition = "INT DEFAULT 0")
    //private int likes;

    //모임 설명
    @Column(name = "club_description", columnDefinition = "TEXT")
    private String clubDescription;

    //모임 내용 요약본
    @Column(name = "meeting_summary", columnDefinition = "TEXT")
    private String meetingSummary;

    @Column(name = "search_index", columnDefinition = "TEXT")
    private String searchIndex;

    @CreationTimestamp
    @Column(name = "created_time", nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(name = "updated_time", nullable = false)
    private LocalDateTime updatedTime;
}
