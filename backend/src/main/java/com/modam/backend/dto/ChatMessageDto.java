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

    @Schema(description = "메시지 타입 (DISCUSSION, ENTER, EXIT 등)", example = "DISCUSSION")
    private MessageType messageType;

    @Schema(description = "북클럽 ID", example = "9")
    private int clubId;

    @Schema(description = "보낸 유저의 ID", example = "1")
    private int userId;

    @Schema(description = "보낸 유저의 이름", example = "사용자1")
    private String userName;

    @Schema(description = "채팅 내용", example = "club_id:9의 첫 메세지")
    private String content;

    @Schema(description = "메시지 생성 시간", example = "2025-04-10 16:50:41")
    private Timestamp createdTime;

    @Schema(description = "SUBTOPIC 순서", example = "1")
    private Integer subtopicOrder;


    // AI 관련 플래그
    private boolean shouldTriggerAiIntro;
    private boolean shouldTriggerFirstDiscussion;

    // 생성자 1: 전체 필드
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
    // 생성자 3: 모든 필드 포함 (AI 플래그 포함)
    public ChatMessageDto(MessageType messageType, int clubId, int userId, String userName,
                          String content, Timestamp createdTime, Integer subtopicOrder,
                          boolean shouldTriggerAiIntro, boolean shouldTriggerFirstDiscussion) {
        this.messageType = messageType;
        this.clubId = clubId;
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.createdTime = createdTime;
        this.subtopicOrder = subtopicOrder;
        this.shouldTriggerAiIntro = shouldTriggerAiIntro;
        this.shouldTriggerFirstDiscussion = shouldTriggerFirstDiscussion;
    }

}
