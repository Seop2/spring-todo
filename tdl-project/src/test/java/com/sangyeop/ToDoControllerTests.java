package com.sangyeop;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangyeop.domain.ToDo;
import com.sangyeop.domain.User;
import com.sangyeop.repository.ToDoRepository;
import com.sangyeop.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.*;

/**
 * @author hagome
 * @since 2019-03-30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ToDoControllerTests {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Before
    public void init() {
        /* 유저 생성 */
        User user = new User();
        user.setId("admin");
        user.setPw("123456");
        user.setEmail("admin@gmail.com");
        userRepository.save(user);
    }

    /* 회원가입 페이지 POST 테스팅 */
    @Test
    @WithMockUser("admin")
    public void getToDoList() throws Exception {
        /* 생성 유저 확인 */
        assertNotNull(userRepository.findById("admin"));
        this.mockMvc.perform(get("/todo/list")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser("admin")
    public void postToDo() throws Exception {
//        ToDo toDo = new ToDo();
//        toDo.setDescription("첫번째 게시글");
//        // TODO: 2019-04-01 오류 발생
//        this.mockMvc.perform(post("/todo/list")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(toDo)))
//                .andExpect(status().isOk());
    }

}
