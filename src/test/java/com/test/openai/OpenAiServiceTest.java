package com.test.openai;

import com.test.openai.model.Answer;
import com.test.openai.model.Question;
import com.test.openai.service.OpenAiService;
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
