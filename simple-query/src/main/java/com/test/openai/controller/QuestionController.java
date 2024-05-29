package com.test.openai.controller;

import com.test.openai.model.Answer;
import com.test.openai.model.CapitalRequest;
import com.test.openai.model.CapitalResponse;
import com.test.openai.model.Question;
import com.test.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {

    @Autowired
    OpenAiService openAiService;

    @PostMapping("/ask")
    public Answer askQuestion(@RequestBody  Question question){
        return openAiService.getAnswer(question);
    }
    @PostMapping("/ask/capital")
    public Answer askCapital(@RequestBody CapitalRequest capital){
        return openAiService.getCapital(capital);
    }

    @PostMapping("/ask/capital-info")
    public CapitalResponse askCapitalWihtInfo(@RequestBody CapitalRequest capital){
        return openAiService.getCapitalWithInfo(capital);
    }

}
