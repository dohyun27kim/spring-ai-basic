package com.samsungsds.springaibasic.service;

import com.samsungsds.springaibasic.model.*;
import reactor.core.publisher.Flux;

public interface ChatService {

    CapitalResponse getCapitalWithInfo(CapitalRequest capitalRequest);
    CapitalResponse getCapital(CapitalRequest capitalRequest);

    String getAnswer(String question);
    Answer getAnswer(Question question);

    Flux<String> streamChat(ChatMessage message);
}