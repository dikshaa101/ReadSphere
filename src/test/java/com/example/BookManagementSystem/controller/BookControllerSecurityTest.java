package com.example.BookManagementSystem.controller;

import com.example.BookManagementSystem.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Test
    void deleteBookWithoutAuthenticationShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isUnauthorized());

        verifyNoInteractions(bookService);
    }

    @Test
    void deleteBookAsRegularUserShouldReturnForbidden() throws Exception {
        mockMvc.perform(delete("/books/1")
                        .with(httpBasic("user", "user123")))
                .andExpect(status().isForbidden());

        verifyNoInteractions(bookService);
    }

    @Test
    void deleteBookAsAdminShouldDeleteBook() throws Exception {
        mockMvc.perform(delete("/books/1")
                        .with(httpBasic("admin", "admin123")))
                .andExpect(status().isOk());

        verify(bookService).deleteBook(1L);
    }
}
