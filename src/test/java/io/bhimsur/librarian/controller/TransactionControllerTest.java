package io.bhimsur.librarian.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bhimsur.librarian.dto.TransactionDto;
import io.bhimsur.librarian.filter.AuthenticationInterceptor;
import io.bhimsur.librarian.service.TransactionService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TransactionController.class)
@WebMvcTest(controllers = TransactionController.class)
public class TransactionControllerTest {

    @MockBean
    private TransactionService transactionService;
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
    public void inquiry() throws Exception {
        when(transactionService.inquiry(any()))
                .thenReturn(new TransactionDto());
        mockMvc.perform(post("/transaction/inquiry")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(new TransactionDto())))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void execute() throws Exception {
        when(transactionService.execute(any()))
                .thenReturn(new TransactionDto());
        mockMvc.perform(post("/transaction/execute")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(new TransactionDto())))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getTransaction() throws Exception {
        when(transactionService.getTransaction(any()))
                .thenReturn(new TransactionDto());
        mockMvc.perform(get("/transaction")
                        .param("transactionId", "TRX2222")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getTransactions() throws Exception {
        when(transactionService.getTransactions(any()))
                .thenReturn(new ArrayList<>());
        mockMvc.perform(get("/transaction")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void returned() throws Exception {
        when(transactionService.returned(any()))
                .thenReturn(new TransactionDto());
        mockMvc.perform(put("/transaction/returned")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(new TransactionDto())))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}