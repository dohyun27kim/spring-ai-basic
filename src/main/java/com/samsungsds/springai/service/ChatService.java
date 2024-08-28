package com.samsungsds.springai.service;

import com.samsungsds.springai.model.*;
import reactor.core.publisher.Flux;

public interface ChatService {

    CapitalResponse getCapitalWithInfo(CapitalRequest capitalRequest);
    CapitalResponse getCapital(CapitalRequest capitalRequest);

    String getAnswer(String question);
    Answer getAnswer(Question question);

    Flux<String> streamChat(ChatMessage message);
}