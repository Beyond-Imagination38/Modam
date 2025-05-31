package com.modam.backend.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "summary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "summary_id")
    private Integer summaryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private BookClub bookClub;

    @Column(name = "topic_number", nullable = false)
    private Integer topicNumber;  // 주제 번호 (1, 2, 3)

    @Column(name = "topic", nullable = false)
    private String topic;  // 주제 질문 예: "윈스턴의 외로움은 어디서 온 것인가?"

    @Column(name = "content", columnDefinition = "TEXT")
    private String content; // 요약 내용
}
