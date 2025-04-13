package com.modam.backend.controller;

import com.modam.backend.dto.ChatMessageDto;
import com.modam.backend.model.BookClub;
import com.modam.backend.model.MessageType;
import com.modam.backend.service.BookClubService;
import com.modam.backend.service.ChatService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    private final BookClubService bookClubService;

    private final RestTemplate restTemplate = new RestTemplate(); //soo: AI 서버 호출용


    public ChatController(ChatService chatService, SimpMessagingTemplate messagingTemplate, BookClubService bookClubService) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
        this.bookClubService = bookClubService;
    }



    @MessageMapping("/chat/{clubId}")
    public void sendMessage(@DestinationVariable int clubId, ChatMessageDto message) {

        System.out.println("수신된 clubId: " + clubId); // debugging
        System.out.println("받은 메시지 DTO: " + message); // debugging

        ChatMessageDto saved = chatService.saveChatMessage(clubId, message);

        System.out.println("DB 저장 후 브로드캐스트 직전 DTO: " + saved); // debugging

        // 클럽 ID 기반 브로드캐스트
        messagingTemplate.convertAndSend("/topic/chat/" + clubId, saved);

        System.out.println("브로드캐스트 완료: /topic/chat/" + clubId); // debugging

        // 추가: ENTER 메시지일 경우 AI 발제문 호출
        if (message.getMessageType() == MessageType.ENTER) {
            BookClub bookClub = bookClubService.getBookClub(clubId);
            int bookId = bookClub.getBook().getBookId();

            List<String> userResponses = List.of(
                    "나는 주인공 윈스턴의 외로움에 공감했어요.",
                    "전체주의 사회의 공포가 정말 생생하게 느껴졌습니다.",
                    "빅브라더의 감시는 현대 사회와도 닮은 것 같아요."
            );

            chatService.sendAiMainTopic(clubId, bookId, userResponses); // AI 진행자 메시지 추가 전송
        }
    }

    @GetMapping("/history/{club_id}")
    public List<ChatMessageDto> getChatHistory(@PathVariable("club_id") int club_id) {
        return chatService.getChatHistory(club_id);
    }


}
