package com.test.openai.demo;

import com.test.openai.demo.model.Answer;
import com.test.openai.demo.model.Question;
import com.test.openai.demo.service.OpenAiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OpenAiServiceTest {

    @Autowired
    OpenAiService openAiService;

    @Test
    void getAnswer(){
        Answer answer = openAiService.getAnswer(new Question("What is the capital of Quebec"));
        System.out.println(answer);
    }
}
