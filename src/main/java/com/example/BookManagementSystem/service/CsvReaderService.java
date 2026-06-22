package com.example.BookManagementSystem.service;

import com.example.BookManagementSystem.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import com.opencsv.CSVReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class CsvReaderService {
    private static final Logger logger =
            LoggerFactory.getLogger(CsvReaderService.class);

    private final ResourceLoader resourceLoader;
    private final String csvLocation;

    public CsvReaderService(ResourceLoader resourceLoader,
                            @Value("${books.csv.location:classpath:books.csv}") String csvLocation) {
        this.resourceLoader = resourceLoader;
        this.csvLocation = csvLocation;
    }

    public List<Book> readBooks() {

        logger.info("Reading books from CSV file from {}", csvLocation);

        List<Book> books = new ArrayList<>();

        if (csvLocation == null || csvLocation.trim().isEmpty()) {
            logger.warn("CSV location is not configured");
            return books;
        }

        String location = csvLocation.trim();

        try {
            Resource resource;

            if (location.startsWith("classpath:")) {
                String path = location.substring("classpath:".length());
                resource = new ClassPathResource(path);
            } else if (location.matches("^[A-Za-z]:\\\\.*") || location.startsWith("\\\\")) {
                // Windows absolute path or UNC path - treat as file
                resource = resourceLoader.getResource("file:" + location);
            } else if (location.startsWith("file:") || location.startsWith("http:") || location.startsWith("https:")) {
                resource = resourceLoader.getResource(location);
            } else {
                // default to classpath
                resource = new ClassPathResource(location);
            }

            if (!resource.exists() || !resource.isReadable()) {
                logger.warn("CSV resource not found or not readable: {}", location);
                return books;
            }

            // Basic filename constraint: must end with .csv
            String filename = resource.getFilename();
            if (filename == null || !filename.toLowerCase().endsWith(".csv")) {
                logger.warn("Configured resource does not appear to be a CSV file: {}", filename);
                return books;
            }

            try (
                    InputStream inputStream = resource.getInputStream();
                    CSVReader reader = new CSVReader(new InputStreamReader(inputStream))
            ) {

                List<String[]> rows = reader.readAll();

                if (rows == null || rows.size() <= 1) {
                    return books;
                }

                for (int i = 1; i < rows.size(); i++) {
                    String[] row = rows.get(i);
                    if (row == null) continue;

                    if (row.length < 10) {
                        logger.warn("Skipping invalid row (insufficient columns) at line {}: {}", i + 1, Arrays.toString(row));
                        continue;
                    }

                    try {
                        // Trim values and validate numeric fields
                        long id = Long.parseLong(row[0].trim());
                        double price = Double.parseDouble(row[5].trim());
                        int quantity = Integer.parseInt(row[6].trim());
                        int publishedYear = Integer.parseInt(row[7].trim());

                        Book book = Book.builder()
                                .id(id)
                                .bookName(row[1].trim())
                                .authorName(row[2].trim())
                                .category(row[3].trim())
                                .publisher(row[4].trim())
                                .price(price)
                                .quantity(quantity)
                                .publishedYear(publishedYear)
                                .isbn(row[8].trim())
                                .language(row[9].trim())
                                .build();

                        books.add(book);

                    } catch (NumberFormatException ex) {
                        logger.warn("Invalid numeric data at line {}: {}", i + 1, Arrays.toString(row));
                    }
                }
            }

        } catch (Exception ex) {
            logger.error("Error reading CSV: {}", ex.getMessage(), ex);
        }

        logger.info("Total books loaded from CSV: {}", books.size());
        return books;
    }
}
