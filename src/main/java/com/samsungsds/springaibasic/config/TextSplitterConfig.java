package com.samsungsds.springaibasic.config;

import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TextSplitterConfig {

    @Bean
    public TextSplitter csvTextSplitter() {
        return new TokenTextSplitter(
                1000,    // defaultChunkSize: 기본 청크 크기
                100,     // minChunkSizeChars: 최소 청크 크기 (문자 수)
                50,      // minChunkLengthToEmbed: 임베딩할 최소 청크 길이
                10,      // maxNumChunks: 최대 청크 수
                false    // keepSeparator: 구분자 유지 여부
        );
    }
}



