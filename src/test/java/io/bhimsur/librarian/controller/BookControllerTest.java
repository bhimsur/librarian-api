package io.bhimsur.librarian.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bhimsur.librarian.dto.BookDto;
import io.bhimsur.librarian.filter.AuthenticationInterceptor;
import io.bhimsur.librarian.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BookController.class)
@WebMvcTest(controllers = BookController.class)
public class BookControllerTest {

    @MockBean
    private BookService bookService;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private AuthenticationInterceptor authenticationInterceptor;
    private MockMvc mockMvc;
    ObjectMapper MAPPER = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        when(authenticationInterceptor.preHandle(any(), any(), any()))
                .thenReturn(true);
    }

    @Test
    public void createBook() throws Exception {
        when(bookService.createBook(any()))
                .thenReturn(BookDto.builder().build());
        mockMvc.perform(post("/book")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(BookDto.builder().build())))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void getBooks() throws Exception {
        when(bookService.getBooks(anyBoolean(), anyBoolean())).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/book")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateBook() throws Exception {
        when(bookService.updateBook(any()))
                .thenReturn(BookDto.builder().build());
        mockMvc.perform(put("/book")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(BookDto.builder().build())))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}