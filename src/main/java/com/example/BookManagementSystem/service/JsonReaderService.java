package com.example.BookManagementSystem.service;

import com.example.BookManagementSystem.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Service
public class JsonReaderService {

    private static final Logger logger =
            LoggerFactory.getLogger(JsonReaderService.class);

    public List<Book> readJson() {

        logger.info("Reading books.json");

        try {

            File file =
                    new File("books.json");

            if (!file.exists()) {

                logger.warn("books.json not found");

                return List.of();
            }

            ObjectMapper mapper =
                    new ObjectMapper();

            List<Book> books =
                    Arrays.asList(
                            mapper.readValue(
                                    file,
                                    Book[].class
                            )
                    );

            logger.info(
                    "Loaded {} books from JSON",
                    books.size()
            );

            return books;

        } catch (Exception ex) {

            logger.error(
                    "Error reading books.json: {}",
                    ex.getMessage()
            );

            return List.of();
        }
    }
}