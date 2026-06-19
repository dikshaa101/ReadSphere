package com.example.BookManagementSystem.service;

import com.example.BookManagementSystem.model.Book;
import org.springframework.stereotype.Service;

import java.io.FileWriter;

@Service
public class CsvWriterService {

    private static final String CSV_FILE =
            "src/main/resources/books.csv";

    public void saveBook(Book book) throws Exception {

        FileWriter writer =
                new FileWriter(CSV_FILE, true);

        writer.write(
                "\n" +
                        book.getId() + "," +
                        book.getBookName() + "," +
                        book.getAuthorName() + "," +
                        book.getCategory() + "," +
                        book.getPublisher() + "," +
                        book.getPrice() + "," +
                        book.getQuantity() + "," +
                        book.getPublishedYear() + "," +
                        book.getIsbn() + "," +
                        book.getLanguage()
        );

        writer.close();
    }
}