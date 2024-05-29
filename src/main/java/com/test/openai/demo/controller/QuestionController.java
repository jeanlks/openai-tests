package com.test.openai.demo.controller;

import com.test.openai.demo.model.Answer;
import com.test.openai.demo.model.CapitalRequest;
import com.test.openai.demo.model.Question;
import com.test.openai.demo.service.OpenAiService;
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
    public Answer askCapitalWihtInfo(@RequestBody CapitalRequest capital){
        return openAiService.getCapitalWithInfo(capital);
    }

}
