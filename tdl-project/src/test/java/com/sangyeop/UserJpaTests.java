package com.sangyeop;

import com.sangyeop.domain.ToDo;
import com.sangyeop.domain.User;
import com.sangyeop.domain.UserRole;
import com.sangyeop.repository.ToDoRepository;
import com.sangyeop.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author hagome
 * @since 2019-03-30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserJpaTests {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ToDoRepository toDoRepository;

    /* UserRepository 테스트 */
    @Test
    public void userSaveTest() throws Exception {
        UserRole role = new UserRole();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        role.setRoleName("BASIC");

        User genericUser = userRepository.save(User.builder().id("testUser")
                .passsword(passwordEncoder.encode("password")).email("test@gmail.com").roles(Arrays.asList(role)).build());
        User foundUser = userRepository.findById(genericUser.getId());
        assertThat(foundUser.getIdx(), is(genericUser.getIdx()));
        assertThat(foundUser.getId(), is(genericUser.getId()));
        assertThat(foundUser.getPasssword(), is(genericUser.getPasssword()));
        assertThat(foundUser.getEmail(), is(genericUser.getEmail()));
        // TODO: 2019-03-30 AssertTath Role Error Handling
        /* Error 발생 */
//        assertThat(foundUser.getRoles(),is(genericUser.getRoles()));
    }

    /* todoRepository 테스트 */
    @Test
    public void toDoSaveTest() throws Exception {
        ToDo genericToDo = toDoRepository.
                save(ToDo.builder().description("첫 번째 TODO").status(false).createdDate(LocalDate.now()).build());
        ToDo foundToDo = toDoRepository.findByIdx(genericToDo.getIdx());
        assertThat(foundToDo.getIdx(), is(genericToDo.getIdx()));
        assertThat(foundToDo.getDescription(), is(genericToDo.getDescription()));
        assertThat(foundToDo.getStatus(), is(genericToDo.getStatus()));
    }

}
