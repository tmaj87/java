package com.example;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;

@Configuration
public class ChatConfig {

    @Bean
    public OllamaChatModel ollamaChatModel() {
        return OllamaChatModel.builder()
                .ollamaApi(new OllamaApi())
                .defaultOptions(OllamaOptions.builder().build())
                .build();
    }

    @Bean
    public ChatClient chatClient(OllamaChatModel ollamaChatModel) {
        // Build a ChatClient using the OllamaChatModel instance
        return ChatClient.builder(ollamaChatModel)
                .build();
    }
}
