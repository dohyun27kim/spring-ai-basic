package com.samsungsds.springaibasic.service;

import com.samsungsds.springaibasic.model.Answer;
import com.samsungsds.springaibasic.model.GetCapitalRequest;
import com.samsungsds.springaibasic.model.GetCapitalResponse;
import com.samsungsds.springaibasic.model.Question;

public interface OpenAIService {

    GetCapitalResponse getCapitalWithInfo(GetCapitalRequest getCapitalRequest);
    GetCapitalResponse getCapital(GetCapitalRequest getCapitalRequest);

    String getAnswer(String question);
    Answer getAnswer(Question question);

}