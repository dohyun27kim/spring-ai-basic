package com.samsungsds.springai.service;

import org.springframework.ai.document.Document;

import java.util.List;

public interface DataService {

    void createDataset();

    List<Document> searchData(String searchQuery);
}
