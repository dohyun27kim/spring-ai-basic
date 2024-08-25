package com.samsungsds.springaibasic.controller;


import com.samsungsds.springaibasic.service.DataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class DataController {

    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping("/store-data")
    @Operation(summary = "Pincone에 데이터를 저장", description = "Pincone에 인덱스가 있는지 확인하고 없으면 데이터를 저장한다. ")
    public void getCapitalWithInfo() {
        dataService.createDataset();
    }

    @PostMapping("/search-data")
    @Operation(summary = "Pincone에 데이터를 조회", description = "Pincone에 있는 데이터를 검색어로 검색한다.")
    public List<Document> searchData(@Parameter(description = "자연어 형태의 질의문") @RequestBody String searchQuery) {
        return dataService.searchData(searchQuery);
    }



}
