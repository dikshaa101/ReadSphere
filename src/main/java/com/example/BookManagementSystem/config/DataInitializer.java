package com.example.BookManagementSystem.config;

import com.example.BookManagementSystem.model.Book;
import com.example.BookManagementSystem.service.CsvReaderService;
import com.example.BookManagementSystem.service.JsonGeneratorService;
import com.example.BookManagementSystem.service.ReportService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final CsvReaderService csvReaderService;
    private final JsonGeneratorService jsonGeneratorService;
    private final ReportService reportService;
    private static final Logger logger =
            LoggerFactory.getLogger(DataInitializer.class);

    @PostConstruct
    public void init() {

        logger.info(
                "Application initialization started"
        );

        try {

            List<Book> books =
                    csvReaderService.readBooks();

            jsonGeneratorService.generateJson(books);

            reportService.generateReport();

            System.out.println(
                    "Application initialized successfully"
            );

        } catch (Exception ex) {

            System.out.println(
                    "Initialization failed: "
                            + ex.getMessage()
            );
        }
        logger.info(
                "CSV -> JSON conversion completed"
        );
    }
}
