package com.samsungsds.springai.service;

import com.samsungsds.springai.model.Answer;
import com.samsungsds.springai.model.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RAGServiceImpl implements RAGService {

    private final ChatClient chatClient;
    private final DataService dataService;
    private final ChatModel chatModel;
    private final VectorStore vectorStore;



    @Value("classpath:prompts/rag-prompt-template.st")
    private Resource ragPromptTemplate;

    public RAGServiceImpl(ChatClient chatClient, DataService dataService, ChatModel chatModel, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.dataService = dataService;
        this.chatModel = chatModel;
        this.vectorStore = vectorStore;
    }


    @Override
    public Answer searchData(Question searchQuery) {
        List<Document> documents = dataService.searchData(searchQuery.question());
        // documents를 하나의 String 형태로 변환
        String strNews = createNewsStr(documents);
        System.out.println(strNews);

        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);
        String promptMessage = String.valueOf(promptTemplate.create(Map.of("input", searchQuery.question(), "documents",
                strNews)));

        //Debug String
        System.out.println(promptMessage);

        return new Answer(chatClient.prompt()
                .user(promptMessage)
                .call()
                .content());
    }

    @Override
    public Answer simpleRag(Question searchQuery) {
        String response = ChatClient.builder(chatModel)
                .build().prompt()
                .advisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()))
                .user(searchQuery.question())
                .call()
                .content();

        return new Answer(response);
    }


    private String createNewsStr(List<Document> documents){
        StringBuilder metadataString = new StringBuilder();
        for(int i = 0 ; i < documents.size(); i++){
            metadataString.append("News : ").append(i).append("\n");
            metadataString.append("====================================\n");
            metadataString.append(maptToStr(documents.get(i).getMetadata()));
            metadataString.append("====================================\n\n");
        }
        return metadataString.toString();
    }

    private String maptToStr(Map<String, Object> map){
        StringBuilder metadataString = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());

            metadataString.append(key)
                    .append(": ")
                    .append(value)
                    .append("\n");
        }

        return metadataString.toString();
    }


}
