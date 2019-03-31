package com.sangyeop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangyeop.domain.User;
import com.sangyeop.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.Assert.*;

/**
 * @author hagome
 * @since 2019-03-30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginControllerTests {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

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
        User user = new User();
        user.setId("test");
        user.setPw("password");
        user.setEmail("Test@gmail.com");

        this.mockMvc.perform(post("/login/sign_up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());

        assertNotNull(userRepository.findById("test"));
    }
    
}
