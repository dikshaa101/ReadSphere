package com.example.BookManagementSystem.service;

import com.example.BookManagementSystem.model.Book;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final JsonReaderService jsonReaderService;
    private static final Logger logger =
            LoggerFactory.getLogger(ReportService.class);

    public void generateReport() throws Exception {

        logger.info("Report generation started");

        List<Book> books = jsonReaderService.readJson();

        if (books.isEmpty()) {

            FileWriter writer =
                    new FileWriter("report.txt");

            writer.write("BOOK MANAGEMENT REPORT\n");
            writer.write("\n");
            writer.write("========================\n\n");
            writer.write("\n");
            writer.write("No book data available.\n");

            writer.close();

            System.out.println("Report Generated Successfully");

            return;
        }

        int totalBooks = books.size();

        double averagePrice =
                books.stream()
                        .mapToDouble(Book::getPrice)
                        .average()
                        .orElse(0);

        int totalQuantity =
                books.stream()
                        .mapToInt(Book::getQuantity)
                        .sum();

        double inventoryValue =
                books.stream()
                        .mapToDouble(
                                book -> book.getPrice() * book.getQuantity()
                        )
                        .sum();

        Book highestPriceBook =
                books.stream()
                        .max(
                                Comparator.comparing(Book::getPrice)
                        )
                        .orElse(null);

        Book lowestPriceBook =
                books.stream()
                        .min(
                                Comparator.comparing(Book::getPrice)
                        )
                        .orElse(null);

        Map<String, Long> categoryCount =
                books.stream()
                        .collect(
                                Collectors.groupingBy(
                                        Book::getCategory,
                                        Collectors.counting()
                                )
                        );

        Map<String, Double> categoryAveragePrice =
                books.stream()
                        .collect(
                                Collectors.groupingBy(
                                        Book::getCategory,
                                        Collectors.averagingDouble(
                                                Book::getPrice
                                        )
                                )
                        );

        Map<String, Long> authorCount =
                books.stream()
                        .collect(
                                Collectors.groupingBy(
                                        Book::getAuthorName,
                                        Collectors.counting()
                                )
                        );

        Map<String, Long> publisherCount =
                books.stream()
                        .collect(
                                Collectors.groupingBy(
                                        Book::getPublisher,
                                        Collectors.counting()
                                )
                        );

        Map<String, Long> languageCount =
                books.stream()
                        .collect(
                                Collectors.groupingBy(
                                        Book::getLanguage,
                                        Collectors.counting()
                                )
                        );

        Map<Integer, Long> yearCount =
                books.stream()
                        .collect(
                                Collectors.groupingBy(
                                        Book::getPublishedYear,
                                        Collectors.counting()
                                )
                        );

        FileWriter writer = new FileWriter("report.txt");

        writer.write("BOOK MANAGEMENT REPORT\n");
        writer.write("==================================================\n\n");

        writer.write("OVERALL STATISTICS\n");
        writer.write("--------------------------------------------------\n");

        writer.write("Total Books : " + totalBooks + "\n");
        writer.write("Average Book Price : " + averagePrice + "\n");
        writer.write("Total Quantity : " + totalQuantity + "\n");
        writer.write("Inventory Value : " + inventoryValue + "\n");

        writer.write(
                "Highest Price Book : "
                        + highestPriceBook.getBookName()
                        + " ("
                        + highestPriceBook.getPrice()
                        + ")\n"
        );

        writer.write(
                "Lowest Price Book : "
                        + lowestPriceBook.getBookName()
                        + " ("
                        + lowestPriceBook.getPrice()
                        + ")\n\n"
        );

        writer.write("CATEGORY WISE REPORT\n");
        writer.write("--------------------------------------------------\n");

        for (Map.Entry<String, Long> entry : categoryCount.entrySet()) {

            writer.write(
                    entry.getKey()
                            + " : "
                            + entry.getValue()
                            + " Books\n"
            );
        }

        writer.write("\n");

        writer.write("CATEGORY AVERAGE PRICE REPORT\n");
        writer.write("--------------------------------------------------\n");

        for (Map.Entry<String, Double> entry : categoryAveragePrice.entrySet()) {

            writer.write(
                    entry.getKey()
                            + " : "
                            + String.format("%.2f", entry.getValue())
                            + "\n"
            );
        }

        writer.write("\n");

        writer.write("PUBLISHER WISE REPORT\n");
        writer.write("--------------------------------------------------\n");

        for (Map.Entry<String, Long> entry : publisherCount.entrySet()) {

            writer.write(
                    entry.getKey()
                            + " : "
                            + entry.getValue()
                            + " Books\n"
            );
        }

        writer.write("\n");

        writer.write("LANGUAGE WISE REPORT\n");
        writer.write("--------------------------------------------------\n");

        for (Map.Entry<String, Long> entry : languageCount.entrySet()) {

            writer.write(
                    entry.getKey()
                            + " : "
                            + entry.getValue()
                            + " Books\n"
            );
        }

        writer.write("\n");

        writer.write("PUBLICATION YEAR REPORT\n");
        writer.write("--------------------------------------------------\n");

        for (Map.Entry<Integer, Long> entry : yearCount.entrySet()) {

            writer.write(
                    entry.getKey()
                            + " : "
                            + entry.getValue()
                            + " Books\n"
            );
        }

        writer.close();

        logger.info(
                "Report generated successfully"
        );
        System.out.println("Report Generated Successfully");
    }

    // New method returning report as string
    public String generateReportString() throws Exception {
        logger.info("Report generation (string) started");

        List<Book> books = jsonReaderService.readJson();
        StringBuilder sb = new StringBuilder();

        if (books.isEmpty()) {
            sb.append("BOOK MANAGEMENT REPORT\n\n");
            sb.append("========================\n\n");
            sb.append("No book data available.\n");

            // write to file for backwards compatibility
            try (FileWriter writer = new FileWriter("report.txt")) {
                writer.write(sb.toString());
            }

            System.out.println("Report Generated Successfully");
            return sb.toString();
        }

        int totalBooks = books.size();

        double averagePrice = books.stream().mapToDouble(Book::getPrice).average().orElse(0);
        int totalQuantity = books.stream().mapToInt(Book::getQuantity).sum();
        double inventoryValue = books.stream().mapToDouble(book -> book.getPrice() * book.getQuantity()).sum();

        Book highestPriceBook = books.stream().max(Comparator.comparing(Book::getPrice)).orElse(null);
        Book lowestPriceBook = books.stream().min(Comparator.comparing(Book::getPrice)).orElse(null);

        Map<String, Long> categoryCount = books.stream().collect(Collectors.groupingBy(Book::getCategory, Collectors.counting()));
        Map<String, Double> categoryAveragePrice = books.stream().collect(Collectors.groupingBy(Book::getCategory, Collectors.averagingDouble(Book::getPrice)));
        Map<String, Long> publisherCount = books.stream().collect(Collectors.groupingBy(Book::getPublisher, Collectors.counting()));
        Map<String, Long> languageCount = books.stream().collect(Collectors.groupingBy(Book::getLanguage, Collectors.counting()));
        Map<Integer, Long> yearCount = books.stream().collect(Collectors.groupingBy(Book::getPublishedYear, Collectors.counting()));

        sb.append("BOOK MANAGEMENT REPORT\n");
        sb.append("==================================================\n\n");

        sb.append("OVERALL STATISTICS\n");
        sb.append("--------------------------------------------------\n");
        sb.append("Total Books : " + totalBooks + "\n");
        sb.append("Average Book Price : " + averagePrice + "\n");
        sb.append("Total Quantity : " + totalQuantity + "\n");
        sb.append("Inventory Value : " + inventoryValue + "\n");

        if (highestPriceBook != null) {
            sb.append("Highest Price Book : " + highestPriceBook.getBookName() + " (" + highestPriceBook.getPrice() + ")\n");
        }
        if (lowestPriceBook != null) {
            sb.append("Lowest Price Book : " + lowestPriceBook.getBookName() + " (" + lowestPriceBook.getPrice() + ")\n\n");
        }

        sb.append("CATEGORY WISE REPORT\n");
        sb.append("--------------------------------------------------\n");
        for (Map.Entry<String, Long> entry : categoryCount.entrySet()) {
            sb.append(entry.getKey() + " : " + entry.getValue() + " Books\n");
        }

        sb.append("\nCATEGORY AVERAGE PRICE REPORT\n");
        sb.append("--------------------------------------------------\n");
        for (Map.Entry<String, Double> entry : categoryAveragePrice.entrySet()) {
            sb.append(entry.getKey() + " : " + String.format("%.2f", entry.getValue()) + "\n");
        }

        sb.append("\nPUBLISHER WISE REPORT\n");
        sb.append("--------------------------------------------------\n");
        for (Map.Entry<String, Long> entry : publisherCount.entrySet()) {
            sb.append(entry.getKey() + " : " + entry.getValue() + " Books\n");
        }

        sb.append("\nLANGUAGE WISE REPORT\n");
        sb.append("--------------------------------------------------\n");
        for (Map.Entry<String, Long> entry : languageCount.entrySet()) {
            sb.append(entry.getKey() + " : " + entry.getValue() + " Books\n");
        }

        sb.append("\nPUBLICATION YEAR REPORT\n");
        sb.append("--------------------------------------------------\n");
        for (Map.Entry<Integer, Long> entry : yearCount.entrySet()) {
            sb.append(entry.getKey() + " : " + entry.getValue() + " Books\n");
        }

        // write to file
        try (FileWriter writer = new FileWriter("report.txt")) {
            writer.write(sb.toString());
        }

        logger.info("Report generated successfully");
        System.out.println("Report Generated Successfully");
        return sb.toString();
    }
}