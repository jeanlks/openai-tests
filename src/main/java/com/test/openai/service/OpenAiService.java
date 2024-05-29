package com.test.openai.service;

import com.test.openai.model.Answer;
import com.test.openai.model.CapitalRequest;
import com.test.openai.model.CapitalResponse;
import com.test.openai.model.Question;
import org.springframework.stereotype.Service;

public interface OpenAiService {

    Answer getAnswer(Question question);

    Answer getCapital(CapitalRequest capitalRequest);

    CapitalResponse getCapitalWithInfo(CapitalRequest capitalRequest);
}
