package com.modam.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.modam.backend.model.MessageType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatMessageDto {

    @Schema(description = "메시지 타입 (DISCUSSION: 의견, SUBTOPIC: 발제문, ENTER: 입장, EXIT: 퇴장)", example = "DISCUSSION")
    private MessageType messageType;

    @Schema(description = "북클럽 ID", example = "9")
    private int clubId;

    @Schema(description = "보낸 유저의 ID", example = "1")
    private int userId;

    @Schema(description = "보낸 유저의 이름", example = "사용자1")
    private String userName;

    @Schema(description = "채팅 내용", example = "안녕하세요, 이 책 정말 재밌네요.")
    private String content;

    @Schema(description = "메시지 생성 시간 (서버 시간 기준)", example = "2025-04-10 16:50:41")
    private Timestamp createdTime;

    @Schema(description = "SUBTOPIC 순서 (첫 발언자부터 1, 2, 3...)", example = "1")
    private Integer subtopicOrder;

    @Schema(description = "채팅 메시지 필터링 결과: 차단된 메시지 여부", example = "false")
    private boolean isBlocked = false;

    @Schema(description = "AI 토픽 생성을 트리거해야 하는 경우 (4명 입장 완료 후)", example = "false")
    private boolean shouldTriggerAiIntro;

    @Schema(description = "사용자 1의 의견 발표를 트리거해야 하는 경우 (4명 발제 완료 후)", example = "false")
    private boolean shouldTriggerFirstDiscussion;

    // 생성자 1: 기본 필드 (AI 관련 플래그 없음)
    public ChatMessageDto(MessageType messageType, int clubId, int userId, String userName,
                          String content, Timestamp createdTime, Integer subtopicOrder) {
        this.messageType = messageType;
        this.clubId = clubId;
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.createdTime = createdTime;
        this.subtopicOrder = subtopicOrder;
    }

    // 생성자 2: subtopicOrder 없이
    public ChatMessageDto(MessageType messageType, int clubId, int userId, String userName,
                          String content, Timestamp createdTime) {
        this(messageType, clubId, userId, userName, content, createdTime, null);
    }

    // 생성자 3: 전체 필드 포함 (AI 플래그 + 차단 여부 포함)
    public ChatMessageDto(MessageType messageType, int clubId, int userId, String userName,
                          String content, Timestamp createdTime, Integer subtopicOrder,
                          boolean shouldTriggerAiIntro,
                          boolean shouldTriggerFirstDiscussion,
                          boolean isBlocked) {
        this.messageType = messageType;
        this.clubId = clubId;
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.createdTime = createdTime;
        this.subtopicOrder = subtopicOrder;
        this.shouldTriggerAiIntro = shouldTriggerAiIntro;
        this.shouldTriggerFirstDiscussion = shouldTriggerFirstDiscussion;
        this.isBlocked = isBlocked;
    }
}
