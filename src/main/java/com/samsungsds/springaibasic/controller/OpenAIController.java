package com.samsungsds.springaibasic.controller;

import com.samsungsds.springaibasic.model.*;
import com.samsungsds.springaibasic.service.OpenAIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @Operation(summary = "포멧이 있는 응답 출력", description = "아래의 프롬프트로 응답 형식을 지정 해 준 수도 관련 질문에 대한 응답을 출력 한다. : <br> " +
            "{country} 나라의 수도는 어디인지와 아래 형태로 대답해줘: <br>" +
            "           \\```country\\``` 나라의 수도는 \\```capital\\```.<br>" +
            "           인구는 \\```population\\```.<br>" +
            "           언어는 \\```language\\```.<br>" +
            "           통화는 \\```currency\\```.")
    public CapitalResponse getCapitalWithInfo(@Parameter(description = "수도를 알고싶은 국가 이름 -> {country}" , example = "대한민국") @RequestBody CapitalRequest capitalRequest) {
        return this.openAIService.getCapitalWithInfo(capitalRequest);
    }

    @PostMapping("/capital")
    @Operation(summary = "", description = "아래의 프롬프트를 실행한다. : <br>" +
            " {country} 나라의 수도는 어디?<br> ")
    public CapitalResponse getCapital(@Parameter(description = "수도를 알고싶은 국가나 주 이름 -> {country}", example = "대한민국") @RequestBody CapitalRequest capitalRequest) {
        return this.openAIService.getCapital(capitalRequest);
    }

    @PostMapping("/ask")
    @Operation(summary = "자유 프롬프트 실행", description = "별도 포멧 없이 사용자의 입력을 그대로 실행")
    public Answer askQuestion(@Parameter(description = "실행 할 프롬프트") @RequestBody Question question) {
        return openAIService.getAnswer(question);
    }
}