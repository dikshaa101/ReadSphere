package com.example.BookManagementSystem.controller;

import com.example.BookManagementSystem.dto.ApiResponse;
import com.example.BookManagementSystem.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<ApiResponse<String>> getReport() throws Exception {
        String report = reportService.generateReportString();
        return ResponseEntity.ok(new ApiResponse<>(200, "Report generated successfully", report));
    }
}
