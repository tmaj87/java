package com.example;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConversationHistoryService {

    // Map sessionId to a list of messages
    private final ConcurrentHashMap<String, List<String>> historyStore = new ConcurrentHashMap<>();

    public List<String> getHistory(String sessionId) {
        return historyStore.computeIfAbsent(sessionId, k -> new ArrayList<>());
    }

    public void addMessage(String sessionId, String message) {
        getHistory(sessionId).add(message);
    }
}
