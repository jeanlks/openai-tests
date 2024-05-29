package com.test.openai.demo.service;

import com.test.openai.demo.model.Answer;
import com.test.openai.demo.model.CapitalRequest;
import com.test.openai.demo.model.CapitalResponse;
import com.test.openai.demo.model.Question;
import org.springframework.stereotype.Service;

public interface OpenAiService {

    Answer getAnswer(Question question);

    Answer getCapital(CapitalRequest capitalRequest);

    CapitalResponse getCapitalWithInfo(CapitalRequest capitalRequest);
}
