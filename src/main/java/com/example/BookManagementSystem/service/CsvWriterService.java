package com.example.BookManagementSystem.service;

import com.example.BookManagementSystem.model.Book;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CsvWriterService {

    @Value("${csv.file.path:books.csv}")
    private String csvLocation;

    private Path resolveCsvPath() throws IOException {
        String loc = (csvLocation == null || csvLocation.isBlank()) ? "books.csv" : csvLocation.trim();

        if (loc.startsWith("classpath:")) {
            String path = loc.substring("classpath:".length());
            Path p = Paths.get(System.getProperty("user.dir")).resolve(path);
            if (p.getParent() != null) Files.createDirectories(p.getParent());
            return p;
        } else if (loc.startsWith("file:")) {
            String pstr = loc.substring("file:".length());
            Path p = Paths.get(pstr);
            if (p.getParent() != null) Files.createDirectories(p.getParent());
            return p;
        } else {
            Path p = Paths.get(loc);
            if (!p.isAbsolute()) p = Paths.get(System.getProperty("user.dir")).resolve(p);
            if (p.getParent() != null) Files.createDirectories(p.getParent());
            return p;
        }
    }

    public void saveBook(Book book) throws IOException {
        Path path = resolveCsvPath();
        boolean writeHeader = !Files.exists(path) || Files.size(path) == 0;

        try (CSVWriter writer = new CSVWriter(new FileWriter(path.toFile(), true))) {
            if (writeHeader) {
                writer.writeNext(new String[]{"id","bookName","authorName","category","publisher","price","quantity","publishedYear","isbn","language"});
            }

            writer.writeNext(new String[]{
                    book.getId() == null ? "" : book.getId().toString(),
                    safeString(book.getBookName()),
                    safeString(book.getAuthorName()),
                    safeString(book.getCategory()),
                    safeString(book.getPublisher()),
                    book.getPrice() == null ? "" : book.getPrice().toString(),
                    book.getQuantity() == null ? "" : book.getQuantity().toString(),
                    book.getPublishedYear() == null ? "" : book.getPublishedYear().toString(),
                    safeString(book.getIsbn()),
                    safeString(book.getLanguage())
            });
            writer.flush();
        }
    }

    public void overwriteBooks(List<Book> books) throws IOException {
        Path path = resolveCsvPath();

        try (CSVWriter writer = new CSVWriter(new FileWriter(path.toFile(), false))) {
            writer.writeNext(new String[]{"id","bookName","authorName","category","publisher","price","quantity","publishedYear","isbn","language"});

            for (Book book : books) {
                writer.writeNext(new String[]{
                        book.getId() == null ? "" : book.getId().toString(),
                        safeString(book.getBookName()),
                        safeString(book.getAuthorName()),
                        safeString(book.getCategory()),
                        safeString(book.getPublisher()),
                        book.getPrice() == null ? "" : book.getPrice().toString(),
                        book.getQuantity() == null ? "" : book.getQuantity().toString(),
                        book.getPublishedYear() == null ? "" : book.getPublishedYear().toString(),
                        safeString(book.getIsbn()),
                        safeString(book.getLanguage())
                });
            }
            writer.flush();
        }
    }

    private String safeString(String s) {
        return s == null ? "" : s;
    }
}