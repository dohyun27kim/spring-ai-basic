package com.samsungsds.springaibasic.controller;

import com.samsungsds.springaibasic.model.*;
import com.samsungsds.springaibasic.service.OpenAIService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenAIController {

    private final OpenAIService openAIService;

    public OpenAIController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/capitalWithInfo")
    public GetCapitalWithInfoResponse getCapitalWithInfo(@RequestBody GetCapitalRequest getCapitalRequest) {
        return this.openAIService.getCapitalWithInfo(getCapitalRequest);
    }

    //JSON
//    @PostMapping("/capitalWithInfo")
//    public Answer getCapitalWithInfo(@RequestBody GetCapitalRequest getCapitalRequest) {
//        return this.openAIService.getCapitalWithInfo(getCapitalRequest);
//    }
    //JSON

    @PostMapping("/capital")
    public GetCapitalResponse getCapital(@RequestBody GetCapitalRequest getCapitalRequest) {
        return this.openAIService.getCapital(getCapitalRequest);
    }

    //JSON
//    @PostMapping("/capital")
//    public Answer getCapital(@RequestBody GetCapitalRequest getCapitalRequest) {
//        return this.openAIService.getCapital(getCapitalRequest);
//    }
    //JSON

    @PostMapping("/ask")
    public Answer askQuestion(@RequestBody Question question) {
        return openAIService.getAnswer(question);
    }
}