package com.samsungsds.springai.reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;


@Component
public class CSVReader {
    private static final String[] HEADERS = {"title", "pubDate", "guid", "description"};

    public List<Document> loadCsvToDocument(String filePath) {
        List<Document> documents = new ArrayList<>();
        Map<String, Object> metadata = new HashMap<>();

        try (CSVParser csvParser = new CSVParser(new FileReader(filePath), CSVFormat.DEFAULT
                .builder()
                .setHeader(HEADERS)
                .setSkipHeaderRecord(true)
                .build())) {
            StringBuilder contentBuilder = new StringBuilder();
            for (CSVRecord csvRecord : csvParser) {
                metadata.clear();
                contentBuilder.setLength(0);

                for (String header : HEADERS) {
                    String value = csvRecord.get(header);
                    metadata.put(header, value);
                }

                // 준비 한 Content와 Metadata를 Document로 만들어서 List에 추가 한다.
                documents.add(new Document((String)metadata.get("title"), new HashMap<>(metadata)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return documents;
    }
}
