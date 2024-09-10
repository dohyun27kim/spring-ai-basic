package com.samsungsds.springai.service;

import com.samsungsds.springai.model.ChatMessage;
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

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent("안녕 니 이름은 뭐니");

        chatService.streamChat(chatMessage)
                .doOnNext(System.out::println)
                .blockLast();

    }


}