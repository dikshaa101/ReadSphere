package com.example.BookManagementSystem.service;

import com.example.BookManagementSystem.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class JsonGeneratorService {

    private static final String JSON_FILE =
            "books.json";

    private static final Logger logger =
            LoggerFactory.getLogger(JsonGeneratorService.class);

    public void generateJson(
            List<Book> books
    ) throws Exception {

        logger.info("Generating books.json");

        ObjectMapper mapper =
                new ObjectMapper();

        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(
                        new File(JSON_FILE),
                        books
                );
        logger.info("books.json generated successfully with {} records",
                books.size());
    }
}