package com.example.BookManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private Long id;
    private String bookName;
    private String authorName;
    private String category;
    private String publisher;
    private Double price;
    private Integer quantity;
    private Integer publishedYear;
    private String isbn;
    private String language;
}