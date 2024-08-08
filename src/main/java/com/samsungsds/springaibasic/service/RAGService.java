package com.samsungsds.springaibasic.service;

import com.samsungsds.springaibasic.model.Answer;
import com.samsungsds.springaibasic.model.Question;

public interface RAGService {

    Answer searchData(Question searchQuery);
}
