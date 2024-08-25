package com.samsungsds.springaibasic.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatServiceImplTest {

    @Autowired
    ChatService chatService;


    @Test
    void getAnswer() {
        String answer = chatService.getAnswer("농담 하나만 해줘");
        System.out.println("Got the answer");
        System.out.println(answer);
    }

    @Test
    void testStreamChat() {

        chatService.streamChat("chatId", "안녕 니 이름은 뭐니")
                .doOnNext(System.out::println)
                .blockLast();

    }


}