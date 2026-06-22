package com.example.BookManagementSystem.service;

import com.example.BookManagementSystem.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final CsvReaderService csvReaderService;
    private final CsvWriterService csvWriterService;
    private final JsonGeneratorService jsonGeneratorService;

    public void addBook(Book book) throws Exception {

        csvWriterService.saveBook(book);

        jsonGeneratorService.generateJson(
                csvReaderService.readBooks()
        );
    }

    public List<Book> getAllBooks() {
        return csvReaderService.readBooks();
    }

    public List<Book> getBooksByCategory(String category) {

        return csvReaderService.readBooks()
                .stream()
                .filter(book ->
                        book.getCategory()
                                .equalsIgnoreCase(category))
                .toList();
    }

    public List<Book> getBooksByAuthor(String author) {

        return csvReaderService.readBooks()
                .stream()
                .filter(book ->
                        book.getAuthorName()
                                .equalsIgnoreCase(author))
                .toList();
    }

    public void deleteBook(int id) throws Exception {

        List<Book> books =
                csvReaderService.readBooks();

        books.removeIf(book ->
                book.getId() == id);

        csvWriterService.overwriteBooks(books);

        jsonGeneratorService.generateJson(books);
    }

    public void updateBook(int id,
                           Book updatedBook)
            throws Exception {

        List<Book> books =
                csvReaderService.readBooks();

        boolean found = false;

        for (int i = 0; i < books.size(); i++) {

            if (books.get(i).getId() == id) {

                books.set(i, updatedBook);

                found = true;
                break;
            }
        }

        if (!found) {
            throw new RuntimeException(
                    "Book not found with id " + id);
        }

        csvWriterService.overwriteBooks(books);

        jsonGeneratorService.generateJson(books);
    }
}