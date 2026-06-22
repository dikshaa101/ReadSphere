package com.example.BookManagementSystem.service;

import com.example.BookManagementSystem.model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonGeneratorServiceTest {

    private final File jsonFile = new File("books.json");

    @AfterEach
    void cleanup() {
        if (jsonFile.exists()) jsonFile.delete();
    }

    @Test
    void generateJson_writesFile() throws Exception {
        JsonGeneratorService service = new JsonGeneratorService();

        Book b = Book.builder()
                .id(1L)
                .bookName("UnitTest Book")
                .authorName("Author")
                .category("Fiction")
                .publisher("Pub")
                .price(9.99)
                .quantity(5)
                .publishedYear(2020)
                .isbn("ISBN-TEST")
                .language("English")
                .build();

        service.generateJson(List.of(b));

        assertTrue(jsonFile.exists(), "books.json should be created");
        assertTrue(jsonFile.length() > 0, "books.json should not be empty");
    }
}
