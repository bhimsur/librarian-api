package io.bhimsur.librarian.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bhimsur.librarian.dto.MemberDto;
import io.bhimsur.librarian.filter.AuthenticationInterceptor;
import io.bhimsur.librarian.service.MemberService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MemberController.class)
@WebMvcTest(controllers = MemberController.class)
public class MemberControllerTest {
    @MockBean
    private MemberService memberService;
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
    public void createMember() throws Exception {
        when(memberService.createMember(any()))
                .thenReturn(new MemberDto());
        mockMvc.perform(post("/member")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(MemberDto.builder().build())))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }
}