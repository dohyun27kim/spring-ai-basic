package com.samsungsds.springai.controller;

import com.samsungsds.springai.model.Answer;
import com.samsungsds.springai.model.Question;
import com.samsungsds.springai.service.RAGService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RAGController {

    private final RAGService ragService;

    public RAGController(RAGService ragService) {
        this.ragService = ragService;
    }


    @PostMapping("/rag-search-data")
    @ResponseBody
    @Operation(summary = "RAG - Pincone에 데이터를 조회를 기반으로 프롬프트 생성하여 LLM에 질의", description = "RAG - Pincone에 데이터를 조회를 기반으로 프롬프트 생성하여 LLM에 질의")
    public Answer searchData(@Parameter(description = "자연어 형태의 질의문") @RequestBody Question searchQuery) {
        return ragService.searchData(searchQuery);
    }

    @PostMapping("/rag-simple")
    @ResponseBody
    @Operation(summary = "RAG - SpringAI가 지원하는 RAG", description = "RAG - SpringAI가 지원하는 RAG를 사용 한 결과")
    public Answer simpleRag(@Parameter(description = "자연어 형태의 질의문") @RequestBody Question searchQuery) {
        return ragService.simpleRag(searchQuery);
    }
}
