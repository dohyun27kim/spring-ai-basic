package com.samsungsds.springaibasic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samsungsds.springaibasic.model.*;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatClient chatClient;

    public OpenAIServiceImpl(ChatClient chatClient, ObjectMapper objectMapper) {
        this.chatClient = chatClient;
    }

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;

    @Value("classpath:templates/get-capital-with-info.st")
    private Resource getCapitalPromptWithInfo;


    @Override
    public GetCapitalResponse getCapitalWithInfo(GetCapitalRequest getCapitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPromptWithInfo);
        String promptMessage = String.valueOf(promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry())));
        String content = chatClient.prompt()
                .user(promptMessage)
                .call()
                .content();

        return new GetCapitalResponse(content);
    }

    @Override
    public GetCapitalResponse getCapital(GetCapitalRequest getCapitalRequest) {

         PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
         String promptMessage = String.valueOf(promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry())));
         String content = chatClient.prompt()
                 .user(promptMessage)
                 .call()
                 .content();

         return new GetCapitalResponse(content);
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
    public String getAnswer(String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }

}