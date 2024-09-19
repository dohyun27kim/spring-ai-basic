package com.samsungsds.springai;

import org.springframework.ai.autoconfigure.vectorstore.pinecone.PineconeVectorStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {PineconeVectorStoreAutoConfiguration.class})
public class SpringAiBasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAiBasicApplication.class, args);
    }

}
