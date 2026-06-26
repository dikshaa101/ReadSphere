package com.example.BookManagementSystem.controller;

import com.example.BookManagementSystem.model.Book;
import com.example.BookManagementSystem.service.BookService;
import com.example.BookManagementSystem.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> addBook(
            @Valid @RequestBody Book book)
            throws Exception {

        bookService.addBook(book);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, "Book added successfully", "Book ID: " + book.getId()));
    }

    @GetMapping
    public List<Book> getAllBooks(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction
    ) {

        return bookService.getAllBooks(
                page,
                size,
                sortBy,
                direction
        );
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<Book>>> getBooksByCategory(
            @PathVariable String category) {

        List<Book> books = bookService.getBooksByCategory(category);
        return ResponseEntity.ok(new ApiResponse<>(200, "Books retrieved by category", books));
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<ApiResponse<List<Book>>> getBooksByAuthor(
            @PathVariable String author) {

        List<Book> books = bookService.getBooksByAuthor(author);
        return ResponseEntity.ok(new ApiResponse<>(200, "Books retrieved by author", books));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody Book book)
            throws Exception {

        bookService.updateBook(id, book);

        return ResponseEntity.ok(new ApiResponse<>(200, "Book updated successfully", "Book ID: " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBook(
            @PathVariable Long id)
            throws Exception {

        bookService.deleteBook(id);

        return ResponseEntity.ok(new ApiResponse<>(200, "Book deleted successfully", "Book ID: " + id));
    }
}