/*
package com.modam.backend.controller;


import com.modam.backend.handler.FreeDiscussionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
*/ /*
@RestController
@RequestMapping("/chat/test")
@RequiredArgsConstructor
public class ChatTestController {

    private final FreeDiscussionManager freeDiscussionManager;

    @GetMapping("/free-discussion/{clubId}/{version}")
    public ResponseEntity<String> triggerFreeDiscussion(
            @PathVariable int clubId,
            @PathVariable int version) {

        System.out.println("📍[컨트롤러] 모니터링 트리거 호출됨");
        freeDiscussionManager.monitorInactivityAndSwitchTopic(clubId, version);

        return ResponseEntity.ok("✅ AI 모니터링 실행됨");
    }
}
*/
