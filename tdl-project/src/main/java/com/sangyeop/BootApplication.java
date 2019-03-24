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
    public CommandLineRunner runner(UserRepository userRepository) throws Exception{
        return args -> {
            userRepository.save(User.builder().id("hagome").passsword("123456").email("hagome@gmail.com").build());
        };
    }

}
