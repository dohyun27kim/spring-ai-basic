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

    public List<Document> loadCsvToDocument(String filePath)  {

        Reader reader;
        CSVParser csvParser;
        List<Document> documents = new ArrayList<>();
        String[] HEADERS = {"title","pubDate","guid","link","description"};
        try {
            reader = new FileReader(filePath);
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader(HEADERS)
                    .setSkipHeaderRecord(true)
                    .build();
            csvParser = new CSVParser(reader, csvFormat);

            StringBuilder stringBuilder = new StringBuilder();


            for (CSVRecord csvRecord : csvParser) {
                //stringbuilder 초기화
                stringBuilder.setLength(0);
                for (int i = 0; i < csvRecord.size(); i++) {
                    stringBuilder.append(csvParser.getHeaderNames().get(i)).append("=> ").append(csvRecord.get(i)).append("\n");
                }

                documents.add(new Document(stringBuilder.toString()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return documents;
    }

}
