package com.modam.backend.model;

public enum MessageType {

    GREETING,             // 인사
    TOPIC_START,          // 대주제 시작
    SUBTOPIC,             // 사용자 의견
    DISCUSSION,           // 일반 토론 메시지
    RAISE_HAND,           // 손들기
    NEXT_TOPIC_VOTE,      // 다음 주제 전환 투표
    NEXT_TOPIC_RESULT,    // 투표 결과
    SESSION_END,          // 토론 종료
    SUMMARY               // AI 요약

}
