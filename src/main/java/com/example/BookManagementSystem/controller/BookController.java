package com.example.BookManagementSystem.controller;

import com.example.BookManagementSystem.model.Book;
import com.example.BookManagementSystem.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public String addBook(
            @Valid @RequestBody Book book)
            throws Exception {

        bookService.addBook(book);

        return "Book Added Successfully";
    }

    @GetMapping
    public List<Book> getAllBooks() {

        return bookService.getAllBooks();
    }

    @GetMapping("/category/{category}")
    public List<Book> getBooksByCategory(
            @PathVariable String category) {

        return bookService
                .getBooksByCategory(category);
    }

    @GetMapping("/author/{author}")
    public List<Book> getBooksByAuthor(
            @PathVariable String author) {

        return bookService
                .getBooksByAuthor(author);
    }

    @PutMapping("/{id}")
    public String updateBook(
            @PathVariable Long id,
            @Valid @RequestBody Book book)
            throws Exception {

        bookService.updateBook(id, book);

        return "Book Updated Successfully";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(
            @PathVariable Long id)
            throws Exception {

        bookService.deleteBook(id);

        return "Book Deleted Successfully";
    }
}