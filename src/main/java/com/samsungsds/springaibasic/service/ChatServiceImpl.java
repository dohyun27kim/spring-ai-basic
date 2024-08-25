package com.samsungsds.springaibasic.service;

import com.samsungsds.springaibasic.model.Answer;
import com.samsungsds.springaibasic.model.CapitalRequest;
import com.samsungsds.springaibasic.model.CapitalResponse;
import com.samsungsds.springaibasic.model.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatClient chatClient;
    private final ChatModel chatmodel;

    public ChatServiceImpl(ChatClient chatClient, ChatModel chatmodel) {
        this.chatClient = chatClient;
        this.chatmodel = chatmodel;
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
    public Flux<String> streamChat(String chatId, String message) {
        return chatClient.prompt()
                .user(message)
                .advisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .advisors(advisorSpec -> advisorSpec
                        .param("CHAT_MEMORY_CONVERSATION_ID_KEY", chatId)
                        .param("CHAT_MEMORY_RETRIEVE_SIZE_KEY", 100))
                .stream()
                .content().concatWith(Flux.just("data: [DONE]\n\n"));
    }


    @Override
    public String getAnswer(String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }



}