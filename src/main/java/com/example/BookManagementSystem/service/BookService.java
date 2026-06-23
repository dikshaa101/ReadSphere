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

        List<Book> books = csvReaderService.readBooks();

        if (book.getId() == null) {
            long maxId = books.stream()
                    .mapToLong(b -> b.getId() == null ? 0L : b.getId())
                    .max()
                    .orElse(0L);
            book.setId(maxId + 1);
        } else {
            boolean exists = books.stream().anyMatch(b -> b.getId() != null && b.getId().equals(book.getId()));
            if (exists) {
                throw new RuntimeException("Book already exists with id " + book.getId());
            }
        }

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

    public void deleteBook(Long id) throws Exception {

        List<Book> books =
                csvReaderService.readBooks();

        boolean removed = books.removeIf(book -> book.getId() != null && book.getId().equals(id));

        if (!removed) {
            throw new RuntimeException("Book not found with id " + id);
        }

        csvWriterService.overwriteBooks(books);

        jsonGeneratorService.generateJson(books);
    }

    public void updateBook(Long id,
                           Book updatedBook)
            throws Exception {

        List<Book> books =
                csvReaderService.readBooks();

        boolean found = false;

        for (int i = 0; i < books.size(); i++) {

            if (books.get(i).getId() != null && books.get(i).getId().equals(id)) {

                updatedBook.setId(id);
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