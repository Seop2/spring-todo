package com.sangyeop;

import com.sangyeop.domain.ToDo;
import com.sangyeop.domain.User;
import com.sangyeop.repository.ToDoRepository;
import com.sangyeop.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDate;
import java.util.stream.IntStream;

@SpringBootApplication
public class BootApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        registrationBean.setFilter(characterEncodingFilter);
        return registrationBean;
    }

    @Bean
    public CommandLineRunner runner(UserRepository userRepository, ToDoRepository toDoRepository) throws Exception {
        return args -> {
            IntStream.rangeClosed(1, 100).forEach(index1 -> {
                User user = userRepository.save(User.builder().id("admin" + index1).email("admin@test.com").passsword("123456").build());

                IntStream.rangeClosed(1, 20).forEach(index2 -> {
                    ToDo toDo = toDoRepository.save(ToDo.builder().description("유저 " + index1 + "번의 해야 할일" + index2).createdDate(LocalDate.now()).status(false).user(user).build());

                });

            });
        };
    }

}
