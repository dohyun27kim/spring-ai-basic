package com.samsungsds.springaibasic.service;

import com.samsungsds.springaibasic.model.Answer;
import com.samsungsds.springaibasic.model.CapitalRequest;
import com.samsungsds.springaibasic.model.CapitalResponse;
import com.samsungsds.springaibasic.model.Question;
import reactor.core.publisher.Flux;

public interface ChatService {

    CapitalResponse getCapitalWithInfo(CapitalRequest capitalRequest);
    CapitalResponse getCapital(CapitalRequest capitalRequest);

    String getAnswer(String question);
    Answer getAnswer(Question question);

    Flux<String> streamChat(String chatId, String message);
}