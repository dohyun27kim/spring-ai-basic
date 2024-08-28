package com.samsungsds.springai.service;

import com.samsungsds.springai.model.Answer;
import com.samsungsds.springai.model.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RAGServiceImpl implements RAGService {

    private final ChatClient chatClient;
    private final DataService dataService;



    @Value("classpath:prompts/rag-prompt-template.st")
    private Resource ragPromptTemplate;

    public RAGServiceImpl(ChatClient chatClient, DataService dataService) {
        this.chatClient = chatClient;
        this.dataService = dataService;
    }


    @Override
    public Answer searchData(Question searchQuery) {
        List<Document> documents = dataService.searchData(searchQuery.question());
        // documents를 하나의 String 형태로 변환
        String strDocuments = documents.stream().map(Document::getContent).reduce("", (a, b) -> a + "\n" + b);

        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);
        String promptMessage = String.valueOf(promptTemplate.create(Map.of("input", searchQuery.question(), "documents",
                strDocuments)));

        //Debug String
        System.out.println(promptMessage);

        return new Answer(chatClient.prompt()
                .user(promptMessage)
                .call()
                .content());
    }


}
