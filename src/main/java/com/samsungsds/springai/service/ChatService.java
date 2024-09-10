package com.samsungsds.springai.service;

import com.samsungsds.springai.model.*;
import reactor.core.publisher.Flux;

public interface ChatService {

    Answer getAnswer(Question question);
    CapitalResponse getCapital(CapitalRequest capitalRequest);

    Flux<String> streamChat(ChatMessage message);
}