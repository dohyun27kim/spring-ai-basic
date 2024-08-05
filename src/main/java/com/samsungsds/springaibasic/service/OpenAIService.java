package com.samsungsds.springaibasic.service;

import com.samsungsds.springaibasic.model.Answer;
import com.samsungsds.springaibasic.model.CapitalRequest;
import com.samsungsds.springaibasic.model.CapitalResponse;
import com.samsungsds.springaibasic.model.Question;

public interface OpenAIService {

    CapitalResponse getCapitalWithInfo(CapitalRequest capitalRequest);
    CapitalResponse getCapital(CapitalRequest capitalRequest);

    String getAnswer(String question);
    Answer getAnswer(Question question);

}