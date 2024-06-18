package com.samsungsds.springaibasic.service;

import com.samsungsds.springaibasic.model.*;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatClient chatClient;

    public OpenAIServiceImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;

    @Value("classpath:templates/get-capital-with-info.st")
    private Resource getCapitalPromptWithInfo;

    //JSON
//    @Autowired
//    ObjectMapper objectMapper;

    //JSON
//    @Override
//    public Answer getCapitalWithInfo(GetCapitalRequest getCapitalRequest) {
//        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPromptWithInfo);
//        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
//        ChatResponse response = chatClient.call(prompt);
//
//        return new Answer(response.getResult().getOutput().getContent());
//    }
    //JSON

    @Override
    public GetCapitalWithInfoResponse getCapitalWithInfo(GetCapitalRequest getCapitalRequest) {
        BeanOutputParser<GetCapitalWithInfoResponse> parser = new BeanOutputParser<>(GetCapitalWithInfoResponse.class);
        String format = parser.getFormat();

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry(),
                "format", format));
        ChatResponse response = chatClient.call(prompt);

        return parser.parse(response.getResult().getOutput().getContent());
    }

    //JSON
    //@Override
    //public Answer getCapital(GetCapitalRequest getCapitalRequest) {

    @Override
    public GetCapitalResponse getCapital(GetCapitalRequest getCapitalRequest) {

        // Json schema
        BeanOutputParser<GetCapitalResponse> parser = new BeanOutputParser<>(GetCapitalResponse.class);
        String format = parser.getFormat();
        System.out.println("Format: " + format);
        // Json schema

        // Json schema
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry(), "format", format));
        ChatResponse response = chatClient.call(prompt);

        System.out.println(response.getResult().getOutput().getContent());
        return parser.parse(response.getResult().getOutput().getContent());
        // Json schema

        //JSON
//        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
//        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
//        ChatResponse response = chatClient.call(prompt);
//        String responseString;
//        try {
//            JsonNode jsonNode = objectMapper.readTree(response.getResult().getOutput().getContent());
//            responseString = jsonNode.get("answer").asText();
//
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        return new Answer(responseString);
        //JSON

    }

    @Override
    public Answer getAnswer(Question question) {
        System.out.println("I was called");

        PromptTemplate promptTemplate = new PromptTemplate(question.question());
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public String getAnswer(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.call(prompt);

        return response.getResult().getOutput().getContent();
    }
}