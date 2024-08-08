package com.samsungsds.springaibasic.service;


import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;

import java.util.List;

@SpringBootTest
public class DocumentReaderTest {

    @Test
    void documentLoaderTest() {
        TikaDocumentReader reader = new TikaDocumentReader(new FileSystemResource("src/main/resources/bbc_news_data.csv"));
        List<Document> documents = reader.read();

        for(Document doc : documents){
            System.out.println(doc.getContent());
        }

    }
}
