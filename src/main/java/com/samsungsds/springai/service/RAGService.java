package com.samsungsds.springai.service;

import com.samsungsds.springai.model.Answer;
import com.samsungsds.springai.model.Question;

public interface RAGService {

    Answer searchData(Question searchQuery);
    Answer simpleRag(Question searchQuery);
}
