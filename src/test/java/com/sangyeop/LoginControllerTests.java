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

import java.util.HashMap;
import java.util.Map;

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
    private final String TEST_ID = "testid";
    private final String TEST_PW = "testpasswd";
    private final String TEST_EMAIL = "test@gmail.com";

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
    public void getSignInPageTest() throws Exception {
        mockMvc.perform(get("/login/sign_up")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /* 회원가입 페이지 GET 테스팅 */
    @Test
    public void getSignUpPageTest() throws Exception {
        mockMvc.perform(get("/login/sign_in")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /* 회원가입 페이지 POST 테스팅 */
    @Test
    public void signUpPostSaveTest() throws Exception {
        User user = new User();
        user.setId(TEST_ID);
        user.setPw(TEST_PW);
        user.setEmail(TEST_EMAIL);

        this.mockMvc.perform(post("/login/sign_up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());

        assertNotNull(userRepository.findById(TEST_ID));
    }

    /* 회원가입 유효성 테스트(ID 중복 체크) */
    @Test
    public void signUpDuplicationTest() throws Exception {

        this.mockMvc.perform(post("/login/sign_up/valid_id")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"newid\"}"))
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/login/sign_up/valid_id")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"testid\"}"))
                .andExpect(status().isBadRequest());
    }

    /* 회원가입 유효성 테스트(ID 미입력) */
    @Test
    public void signUpInValidIdNotBlankTest() throws Exception {
        User user = new User();
        user.setId("");
        user.setPw(TEST_PW);
        user.setEmail(TEST_EMAIL);

        this.mockMvc.perform(post("/login/sign_up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }


    /* 회원가입 유효성 테스트(ID 길이 5이상) */
    @Test
    public void signUpInValidIdMinLengthTest() throws Exception {
        User user = new User();
        user.setId("test");
        user.setPw(TEST_PW);
        user.setEmail(TEST_EMAIL);

        this.mockMvc.perform(post("/login/sign_up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }


    /* 회원가입 유효성 테스트(ID 길이 9이하) */
    @Test
    public void signUpInValidIdMaxLengthTest() throws Exception {
        User user = new User();
        user.setId("abcdefghij");
        user.setPw(TEST_PW);
        user.setEmail(TEST_EMAIL);

        this.mockMvc.perform(post("/login/sign_up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    /* 회원가입 유효성 테스트(PW 미입력) */
    @Test
    public void signUpInValidPWNotBlankTest() throws Exception {
        User user = new User();
        user.setId(TEST_ID);
        user.setPw("");
        user.setEmail(TEST_EMAIL);

        this.mockMvc.perform(post("/login/sign_up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    /* 회원가입 유효성 테스트(PW 길이 9이하) */
    @Test
    public void signUpInValidPWMinLengthTest() throws Exception {
        User user = new User();
        user.setId(TEST_ID);
        user.setPw("12345678");
        user.setEmail(TEST_EMAIL);

        this.mockMvc.perform(post("/login/sign_up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    /* 회원가입 유효성 테스트(E-mail BAD format) */
    @Test
    public void signUpInValidEmailFormatTest() throws Exception {
        User user = new User();
        user.setId(TEST_ID);
        user.setPw(TEST_PW);
        user.setEmail("@gmail.com");

        this.mockMvc.perform(post("/login/sign_up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

}
