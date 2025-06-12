package com.modam.backend.model;

public enum MessageType {

    DISCUSSION_NOTICE,
    ENTER,                //자동으로 AI 발제문 호출
    MAINTOPIC,             //대주제
    TOPIC_START,          // 대주제 시작
    SUBTOPIC,             // 사용자 의견
    DISCUSSION,           // 일반 토론 메시지
    FREE_DISCUSSION_NOTICE,      // 자유토론 알림
    FREE_DISCUSSION,      // 자유토론
    END_NOTICE,          // 토론 종료
    SUMMARY               // AI 요약

}
