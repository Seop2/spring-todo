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

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void testGetSignIn() throws Exception {
        mockMvc.perform(get("/login/sign_up")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /* 회원가입 페이지 GET 테스팅 */
    @Test
    public void testGetSignUp() throws Exception {
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
        user.setEmail("test@gmail.com");

        this.mockMvc.perform(post("/login/sign_up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());

        assertNotNull(userRepository.findById("test"));
    }

    /* 회원가입 유효성 테스트(ID 미입력) */
    @Test
    public void testPostSignUpValidID() throws Exception{
        User user = new User();
        user.setId("");
        user.setPw("password");
        user.setEmail("test@gmail.com");

        this.mockMvc.perform(post("/login/sign_up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    /* 회원가입 유효성 테스트(PW 미입력) */
    @Test
    public void testPostSignUpValidPW() throws Exception{
        User user = new User();
        user.setId("test");
        user.setPw("");
        user.setEmail("test@gmail.com");

        this.mockMvc.perform(post("/login/sign_up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    /* 회원가입 유효성 테스트(E-mail BAD format) */
    @Test
    public void testPostSignUpValidEmail() throws Exception{
        User user = new User();
        user.setId("test");
        user.setPw("password");
        user.setEmail("@gmail.com");

        this.mockMvc.perform(post("/login/sign_up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }
    
}
