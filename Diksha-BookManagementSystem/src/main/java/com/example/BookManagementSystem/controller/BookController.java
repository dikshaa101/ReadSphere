package com.example.BookManagementSystem.controller;

import com.example.BookManagementSystem.model.Book;
import com.example.BookManagementSystem.service.BookService;
import com.example.BookManagementSystem.service.JsonReaderService;
import com.example.BookManagementSystem.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final JsonReaderService jsonReaderService;
    private final ReportService reportService;
    private final BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() throws Exception {

        return jsonReaderService.readJson();
    }

    @PostMapping
    public String addBook(
            @RequestBody Book book
    ) throws Exception {

        bookService.addBook(book);

        return "Book Added Successfully";
    }

    @GetMapping("/reports")
    public String getReport() throws Exception {

        reportService.generateReport();

        return Files.readString(
                Path.of("report.txt")
        );
    }
}