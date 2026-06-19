package com.example.BookManagementSystem.service;

import com.example.BookManagementSystem.model.Book;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final CsvWriterService csvWriterService;
    private final CsvReaderService csvReaderService;
    private final JsonGeneratorService jsonGeneratorService;
    private static final Logger logger =
            LoggerFactory.getLogger(BookService.class);


    public void addBook(Book book) throws Exception {

        logger.info(
                "Adding new book: {}",
                book.getBookName()
        );

        // Save to CSV
        csvWriterService.saveBook(book);

        logger.info(
                "Book added to CSV successfully"
        );

        // Read latest CSV
        List<Book> books =
                csvReaderService.readBooks();

        // Generate fresh JSON
        jsonGeneratorService.generateJson(books);

        logger.info(
                "JSON synchronized successfully"
        );
    }
}