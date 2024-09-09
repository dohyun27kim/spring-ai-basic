package com.samsungsds.springai.reader;


import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class CSVReaderTest {

    @Autowired
    CSVReader csvReader;

    @Test
    void cSVReaderTest() {

        List<Document> documents = csvReader.loadCsvToDocument("src/main/resources/bbc_news.csv");

        for(int i = 0 ; i < 10 ; i++){
            System.out.println(documents.get(i).getContent());
            System.out.println("Metadata:");
            for (Map.Entry<String, Object> entry : documents.get(i).getMetadata().entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }

    }
}
