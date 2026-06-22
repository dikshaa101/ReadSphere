package com.example.BookManagementSystem.service;

import com.example.BookManagementSystem.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderServiceTest {

    private final File jsonFile = new File("books.json");

    @AfterEach
    void cleanup() {
        if (jsonFile.exists()) jsonFile.delete();
    }

    @Test
    void readJson_readsFile() throws Exception {
        Book b = Book.builder()
                .id(2L)
                .bookName("ReaderTest Book")
                .authorName("Author2")
                .category("Non-Fiction")
                .publisher("Pub2")
                .price(15.5)
                .quantity(3)
                .publishedYear(2021)
                .isbn("ISBN-READ")
                .language("English")
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, List.of(b));

        JsonReaderService service = new JsonReaderService();
        List<Book> books = service.readJson();

        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals("ReaderTest Book", books.get(0).getBookName());
    }
}
