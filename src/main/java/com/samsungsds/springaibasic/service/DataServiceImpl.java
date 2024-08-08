package com.samsungsds.springaibasic.service;

import com.samsungsds.springaibasic.reader.CSVReader;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DataServiceImpl implements DataService {

    private final VectorStore vectorStore;
    private final CSVReader csvReader;

    public DataServiceImpl(VectorStore vectorStore, CSVReader csvReader) {
        this.vectorStore = vectorStore;
        this.csvReader = csvReader;
    }

    @Override
    public void createDataset() {
        vectorStore.add(csvReader.loadCsvToDocument("src/main/resources/bbc_news_data.csv"));
    }

    @Override
    public List<Document> searchData(String searchQuery) {
        return vectorStore.similaritySearch(SearchRequest.query(searchQuery).withTopK(5));
    }



}
