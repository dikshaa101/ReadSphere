package com.example.BookManagementSystem.service;

import com.example.BookManagementSystem.model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    JsonReaderService jsonReaderService;

    @InjectMocks
    ReportService reportService;

    private final File reportFile = new File("report.txt");

    @AfterEach
    void cleanup() throws Exception {
        if (reportFile.exists()) Files.delete(reportFile.toPath());
    }

    @Test
    void generateReport_writesReport_whenBooksPresent() throws Exception {
        Book b = Book.builder()
                .id(3L)
                .bookName("Report Book")
                .authorName("Auth")
                .category("Sci")
                .publisher("P")
                .price(20.0)
                .quantity(2)
                .publishedYear(2019)
                .isbn("ISBN-R")
                .language("English")
                .build();

        when(jsonReaderService.readJson()).thenReturn(List.of(b));

        reportService.generateReport();

        assertTrue(reportFile.exists(), "report.txt should be created");
        String content = Files.readString(reportFile.toPath());
        assertTrue(content.contains("Total Books"), "Report should contain overall statistics");
    }
}
