package com.example.BookManagementSystem.service;

import com.example.BookManagementSystem.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

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

    public List<Book> readBooks() {

        logger.info("Reading books from CSV file");

        List<Book> books = new ArrayList<>();


        try {

            ClassPathResource resource =
                    new ClassPathResource("books.csv");

            if (!resource.exists()) {

                System.out.println("books.csv not found");

                return books;
            }

            try (
                    InputStream inputStream =
                            resource.getInputStream();

                    CSVReader reader =
                            new CSVReader(
                                    new InputStreamReader(inputStream)
                            )
            ) {

                List<String[]> rows =
                        reader.readAll();

                if (rows == null || rows.size() <= 1) {

                    return books;
                }

                for (int i = 1; i < rows.size(); i++) {

                    String[] row =
                            rows.get(i);

                    if (row.length < 10) {

                        System.out.println(
                                "Skipping invalid row: "
                                        + Arrays.toString(row)
                        );

                        continue;
                    }

                    try {

                        Book book =
                                new Book(
                                        Long.parseLong(row[0]),
                                        row[1],
                                        row[2],
                                        row[3],
                                        row[4],
                                        Double.parseDouble(row[5]),
                                        Integer.parseInt(row[6]),
                                        Integer.parseInt(row[7]),
                                        row[8],
                                        row[9]
                                );

                        books.add(book);

                    } catch (NumberFormatException ex) {

                        System.out.println(
                                "Invalid numeric data: "
                                        + Arrays.toString(row)
                        );
                    }
                }
            }

        } catch (Exception ex) {

            System.out.println(
                    "Error reading CSV: "
                            + ex.getMessage()
            );
        }

        logger.info("Total books loaded from CSV: {}", books.size());
        return books;
    }
}