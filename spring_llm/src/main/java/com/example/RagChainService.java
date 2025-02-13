package com.example;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RagChainService {

    private final ChatClient chatClient;
    private final ConversationHistoryService historyService;

    public RagChainService(ChatClient chatClient, ConversationHistoryService historyService) {
        this.chatClient = chatClient;
        this.historyService = historyService;
    }

    public String generateAnswer(String sessionId, String query) {
        // Retrieve conversation history (if any)
        List<String> history = historyService.getHistory(sessionId);

        // Simulate retrieval of context (replace with a real retriever if available)
        String retrievedContext = "Relevant context from your documents or knowledge base.";

        // Build the combined prompt:
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("CONTEXT:\n")
                .append(retrievedContext)
                .append("\n\nCHAT HISTORY:\n");
        for (String msg : history) {
            promptBuilder.append(msg).append("\n");
        }
        promptBuilder.append("\nUSER: ").append(query).append("\n");

        String prompt = promptBuilder.toString();

        // Call the local Ollama model via ChatClient
        String answer = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        // Update conversation history
        historyService.addMessage(sessionId, "USER: " + query);
        historyService.addMessage(sessionId, "AI: " + answer);

        return answer;
    }
}
