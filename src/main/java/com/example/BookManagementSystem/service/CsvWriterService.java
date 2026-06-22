package com.example.BookManagementSystem.service;

import com.example.BookManagementSystem.model.Book;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Service
public class CsvWriterService {

    public void saveBook(Book book) throws IOException {

        try (PrintWriter writer =
                     new PrintWriter(
                             new FileWriter("books.csv", true))) {

            writer.println(
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
        }
    }

    public void overwriteBooks(List<Book> books)
            throws IOException {

        try (PrintWriter writer =
                     new PrintWriter(
                             new FileWriter("books.csv"))) {

            writer.println(
                    "id,bookName,authorName,category,publisher,price,quantity,publishedYear,isbn,language");

            for (Book book : books) {

                writer.println(
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
            }
        }
    }
}