package com.samsungsds.springai.service;

import com.samsungsds.springai.model.*;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.UUID;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatClient chatClient;

    public ChatServiceImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Value("classpath:prompts/get-capital-prompt.st")
    private Resource capitalPrompt;

    @Value("classpath:prompts/get-capital-with-info.st")
    private Resource capitalPromptWithInfo;


    @Override
    public CapitalResponse getCapitalWithInfo(CapitalRequest capitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(capitalPromptWithInfo);

        String promptMessage = String.valueOf(promptTemplate.create(Map.of("country", capitalRequest.country())));
        String content = chatClient.prompt()
                .user(promptMessage)
                .call()
                .content();

        return new CapitalResponse(content);
    }

    @Override
    public CapitalResponse getCapital(CapitalRequest capitalRequest) {

         PromptTemplate promptTemplate = new PromptTemplate(capitalPrompt);
         String promptMessage = String.valueOf(promptTemplate.create(Map.of("country", capitalRequest.country())));
         String content = chatClient.prompt()
                 .user(promptMessage)
                 .call()
                 .content();

         return new CapitalResponse(content);
    }

    @Override
    public Answer getAnswer(Question question) {
        String content = chatClient.prompt()
                .user(question.question())
                .call()
                .content();
        return new Answer(content);
    }

    @Override
    public Flux<String> streamChat(ChatMessage message) {
        String sessionId = message.getSessionId();
        if (sessionId == null || sessionId.isEmpty()) {
            sessionId = UUID.randomUUID().toString();
            message.setSessionId(sessionId);
        }

        return chatClient.prompt()
                .user(message.getContent())
                .advisors(advisorSpec -> advisorSpec
                        .param(CHAT_MEMORY_CONVERSATION_ID_KEY, message.getSessionId())
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
                .stream()
                .content();
    }


    @Override
    public String getAnswer(String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }



}