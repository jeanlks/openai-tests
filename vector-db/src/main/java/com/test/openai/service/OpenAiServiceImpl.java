package com.test.openai.service;

import com.test.openai.model.Answer;
import com.test.openai.model.CapitalRequest;
import com.test.openai.model.CapitalResponse;
import com.test.openai.model.Question;
import org.slf4j.Logger;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OpenAiServiceImpl implements OpenAiService {


    @Autowired
    Logger log ;

    ChatClient client;
    VectorStore vectorStore;

    public OpenAiServiceImpl(ChatClient client, VectorStore vectorStore) {
        this.client = client;
        this.vectorStore = vectorStore;
    }



    @Value("classpath:templates/getCapital.st")
    Resource capitalPromptTemplate;

    @Value("classpath:templates/ragPromptTemplate.st")
    Resource ragPromptTemplate;

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

    @Override
    public Answer getVectorAnswer(Question question) {
        List<Document> documents = vectorStore
                .similaritySearch(SearchRequest.query(question.question()).withTopK(1));
        log.info("Similarity: "+ documents);
        List<String> contentList = documents.stream().map(Document::getContent).toList();
        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);
        Prompt prompt = promptTemplate.create(Map.of("input", question.question(),
                "documents",String.join("\n", contentList)));
        ChatResponse chatResponse = client.call(prompt);
        return  new Answer(chatResponse.getResult().getOutput().getContent());
    }


}
