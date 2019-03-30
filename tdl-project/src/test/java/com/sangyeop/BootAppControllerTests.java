package com.sangyeop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangyeop.controller.LoginController;
import com.sangyeop.domain.UserRequestDto;
import com.sangyeop.domain.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author hagome
 * @since 2019-03-30
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class BootAppControllerTests {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /* 로그인 페이지 GET 테스팅 */
    @Test
    public void testGetSign_in() throws Exception {
        mockMvc.perform(get("/login/sign_up")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /* 회원가입 페이지 GET 테스팅 */
    @Test
    public void testGetSign_up() throws Exception {
        mockMvc.perform(get("/login/sign_in")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /* 회원가입 페이지 POST 테스팅 */
    @Test
    public void testPostSign_up() throws Exception {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setId("test");
        userRequestDto.setPw("password");
        userRequestDto.setEmail("Test@gmail.com");

        this.mockMvc.perform(post("/login/sign_up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(status().isCreated());
    }
    
}
