package com.samsungsds.springaibasic.service;

import com.samsungsds.springaibasic.model.Answer;
import com.samsungsds.springaibasic.model.CapitalRequest;
import com.samsungsds.springaibasic.model.CapitalResponse;
import com.samsungsds.springaibasic.model.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatClient chatClient;
    private final ChatModel chatmodel;

    public ChatServiceImpl(ChatClient chatClient, ChatModel chatmodel) {
        this.chatClient = chatClient;
        this.chatmodel = chatmodel;
    }

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource capitalPrompt;

    @Value("classpath:templates/get-capital-with-info.st")
    private Resource capitalPromptWithInfo;


    @Override
    public CapitalResponse getCapitalWithInfo(CapitalRequest capitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(capitalPromptWithInfo);

        String promptMessage = String.valueOf(promptTemplate.create(Map.of("country", capitalRequest.country())));
        String content = chatClient.prompt()
                .user(promptMessage)
                .call()
                .content();


        this.chatmodel.call(new Prompt(promptMessage));


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
    public String getAnswer(String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }

}