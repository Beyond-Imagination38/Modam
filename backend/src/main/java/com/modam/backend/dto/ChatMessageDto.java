package com.modam.backend.dto;

import com.modam.backend.model.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {

    private MessageType messageType;

    private int clubId;
    private int userId;
    private String userName;
    private String content;
    private Timestamp createdTime;

    // 올바른 생성자
    public ChatMessageDto(int clubId, int userId, String userName, String content, Timestamp createdTime) {
        this.messageType = MessageType.DISCUSSION;
        this.clubId = clubId;
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.createdTime = createdTime;
    }
}
