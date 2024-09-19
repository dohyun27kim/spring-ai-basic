package com.samsungsds.springai.config;

import io.pinecone.PineconeControlPlaneClient;
import io.pinecone.exceptions.PineconeException;
import org.openapitools.client.model.*;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.PineconeVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PineconeConfig {

    @Value("${spring.ai.vectorstore.pinecone.apiKey}")
    private String pineconeApiKey;

    @Value("${spring.ai.vectorstore.pinecone.index-name}")
    private String indexName;

    @Bean
    public PineconeVectorStore.PineconeVectorStoreConfig createIndex() {
        PineconeControlPlaneClient client = new PineconeControlPlaneClient(pineconeApiKey);
        IndexModel indexModel = getIndexModel(client);
        String environment = parseEnvironment(indexModel.getHost());
        String projectId = parseProjectId(indexModel.getHost());

        return PineconeVectorStore.PineconeVectorStoreConfig.builder()
                .withApiKey(pineconeApiKey)
                .withEnvironment(environment)
                .withProjectId(projectId)
                .withIndexName(indexName)
                .build();
    }

    @Bean
    public VectorStore vectorStore(PineconeVectorStore.PineconeVectorStoreConfig config, EmbeddingModel embeddingModel) {
        return new PineconeVectorStore(config, embeddingModel);
    }

    private IndexModel getIndexModel(PineconeControlPlaneClient client) {
        try {
            return client.describeIndex(indexName);
        } catch (PineconeException e) {
            return createIndex(client);
        }
    }

    private IndexModel createIndex(PineconeControlPlaneClient client) {
        ServerlessSpec serverlessSpec = new ServerlessSpec()
                .cloud(ServerlessSpec.CloudEnum.AWS)
                .region("us-east-1");
        CreateIndexRequestSpec createIndexRequestSpec = new CreateIndexRequestSpec()
                .serverless(serverlessSpec);
        CreateIndexRequest createIndexRequest = new CreateIndexRequest()
                .name(indexName)
                .metric(IndexMetric.COSINE)
                .dimension(1536)
                .spec(createIndexRequestSpec);

        return client.createIndex(createIndexRequest);
    }

    private String parseEnvironment(String hostUrl) {
        int startIndex = hostUrl.indexOf("svc.") + 4;
        int endIndex = hostUrl.indexOf(".pinecone.io");
        return hostUrl.substring(startIndex, endIndex);
    }

    private String parseProjectId(String hostUrl) {
        int startIndex = hostUrl.indexOf(indexName + "-") + indexName.length() + 1;
        int endIndex = hostUrl.indexOf(".svc");
        return hostUrl.substring(startIndex, endIndex);
    }
}