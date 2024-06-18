package com.samsungsds.springaibasic.service;

import com.samsungsds.springaibasic.model.Answer;
import com.samsungsds.springaibasic.model.GetCapitalRequest;
import com.samsungsds.springaibasic.model.GetCapitalResponse;
import com.samsungsds.springaibasic.model.GetCapitalWithInfoResponse;
import com.samsungsds.springaibasic.model.Question;

public interface OpenAIService {


    GetCapitalWithInfoResponse getCapitalWithInfo(GetCapitalRequest getCapitalRequest);

    //JSON
    //Answer getCapitalWithInfo(GetCapitalRequest getCapitalRequest);

    //JSON
    //Answer getCapital(GetCapitalRequest getCapitalRequest);

    // Json schema
    GetCapitalResponse getCapital(GetCapitalRequest getCapitalRequest);

    String getAnswer(String question);
    Answer getAnswer(Question question);

}