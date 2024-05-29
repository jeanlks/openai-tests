package com.test.openai.bootstrap;

import com.test.openai.model.VectorStoreProperties;
import org.slf4j.Logger;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoadVectorStore implements CommandLineRunner {

    @Autowired
    Logger log ;


    VectorStore vectorStore;

    public LoadVectorStore(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Autowired
    VectorStoreProperties vectorStoreProperties;

    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing vector store...");
        if(vectorStore.similaritySearch("westmount").isEmpty()){
            vectorStoreProperties.getDocumentsToLoad().forEach(document -> {
                log.info("Loading document: "+document.getFilename());
                TextReader textReader = new TextReader(document);
                List<Document> documents = textReader.get();
                TextSplitter textSplitter = new TokenTextSplitter();
                List<Document> splitDocuments = textSplitter.apply(documents);
                vectorStore.add(splitDocuments);
            });
            log.info("VectorStore loaded");
        }
    }
}
