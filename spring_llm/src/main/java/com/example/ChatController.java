package com.example;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final RagChainService ragChainService;

    public ChatController(RagChainService ragChainService) {
        this.ragChainService = ragChainService;
    }

    // For example: GET /chat?sessionId=abc123&query=Tell me a joke
    @GetMapping
    public String chat(@RequestParam String sessionId, @RequestParam String query) {
        return ragChainService.generateAnswer(sessionId, query);
    }
}
