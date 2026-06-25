package com.example.BookManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    private Long id;

    @NotEmpty(message = "Book name cannot be empty")
    @Size(min = 2, max = 200, message = "Book name must be between 2 and 200 characters")
    private String bookName;

    @NotEmpty(message = "Author name cannot be empty")
    @Size(min = 2, max = 100, message = "Author name must be between 2 and 100 characters")
    private String authorName;

    @NotEmpty(message = "Category cannot be empty")
    @Size(min = 2, max = 50, message = "Category must be between 2 and 50 characters")
    private String category;

    @NotEmpty(message = "Publisher cannot be empty")
    @Size(min = 2, max = 100, message = "Publisher must be between 2 and 100 characters")
    private String publisher;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @DecimalMax(value = "99999.99", message = "Price cannot exceed 99999.99")
    private Double price;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity cannot be negative")
    @Max(value = 10000, message = "Quantity cannot exceed 10000")
    private Integer quantity;

    @NotNull(message = "Published year cannot be null")
    @Min(value = 1000, message = "Published year must be at least 1000")
    @Max(value = 2100, message = "Published year cannot exceed 2100")
    private Integer publishedYear;

    @NotEmpty(message = "ISBN cannot be empty")
    @Pattern(regexp = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?:(?=(?:[0-9]+[- ]){3})[0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[0-9]{14}$)|(?=(?:[0-9X]+[- ]){3})[0-9X]{13}$|97(?:8|9)[- ]?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9]|(?=.{17}$)97[89][- ]?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9X]).*", 
    message = "Invalid ISBN format")
    private String isbn;

    @NotEmpty(message = "Language cannot be empty")
    @Size(min = 2, max = 50, message = "Language must be between 2 and 50 characters")
    private String language;
}