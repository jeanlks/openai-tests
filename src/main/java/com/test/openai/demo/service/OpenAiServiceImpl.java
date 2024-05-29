package com.test.openai.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.openai.demo.model.Answer;
import com.test.openai.demo.model.CapitalRequest;
import com.test.openai.demo.model.CapitalResponse;
import com.test.openai.demo.model.Question;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OpenAiServiceImpl implements OpenAiService {

    private final ChatClient client;

    public OpenAiServiceImpl(ChatClient client) {
        this.client = client;
    }

    @Value("classpath:templates/getCapital.st")
    Resource capitalPromptTemplate;


    @Value("classpath:templates/getCapitalInfo.st")
    Resource capitalPromptTemplateWithInfo;

    @Override
    public Answer getAnswer(Question question) {
        PromptTemplate promptTemplate = new PromptTemplate(question.question());
        Prompt prompt = promptTemplate.create();
        ChatResponse chatResponse = client.call(prompt);

        return new Answer(chatResponse.getResult().getOutput().getContent());
    }

    @Override
    public Answer getCapital(CapitalRequest capitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(capitalPromptTemplate);
        Prompt prompt = promptTemplate.create(Map.of("country", capitalRequest.country()));
        ChatResponse chatResponse = client.call(prompt);

        return new Answer(chatResponse.getResult().getOutput().getContent());
    }

    @Override
    public CapitalResponse getCapitalWithInfo(CapitalRequest capitalRequest) {
        BeanOutputParser<CapitalResponse> parser = new BeanOutputParser<>(CapitalResponse.class);
        String format = parser.getFormat();
        PromptTemplate promptTemplate = new PromptTemplate(capitalPromptTemplateWithInfo);
        Prompt prompt = promptTemplate.create(Map.of("country", capitalRequest.country(),
                                                    "format", format));
        ChatResponse chatResponse = client.call(prompt);
        return parser.parse(chatResponse.getResult().getOutput().getContent());
    }
}
