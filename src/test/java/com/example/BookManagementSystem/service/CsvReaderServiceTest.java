package com.example.BookManagementSystem.service;

import com.example.BookManagementSystem.model.Book;
import org.junit.jupiter.api.Test;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CsvReaderServiceTest {

    @Test
    void readBooks_fromClasspath_shouldReturnBooks() {
        ResourceLoader rl = new DefaultResourceLoader();
        CsvReaderService service = new CsvReaderService(rl, "classpath:books.csv");

        List<Book> books = service.readBooks();

        assertNotNull(books);
        assertFalse(books.isEmpty(), "Expected at least one book from classpath:books.csv");
    }
}
