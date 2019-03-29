package com.sangyeop.config;

import com.sangyeop.handler.CustomLoginSuccessHandler;
import com.sangyeop.service.CustomUserDeatilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * @author hagome
 * @since  2019-03-29
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /* CustomUser */
    @Autowired
    CustomUserDeatilsService customUserDeatilsService;

    /* Password Encoder 등록 */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* 로그인 */
    /* 인증방식 */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDeatilsService).passwordEncoder(passwordEncoder());
    }

    /* Security 제외 패턴 */

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        web.ignoring().antMatchers("/css/**", "/script/**", "image/**", "/fonts/**", "lib/**", "/js/**");
    }

    /* 시큐어 패턴 등록 */
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);

        http.authorizeRequests()
                .antMatchers("/login/**", "/error").permitAll() /* 해당 패턴 허용 */
                .anyRequest().authenticated() /* 나머진 모두 인증이 필요 */
                .and()
                .formLogin() /* 로그인 폼이 나오도록 */
                .loginPage("/login/sign_in") /* 내 로그인 페이지 */
                .successHandler(successHandler())
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login/sign_in")
                .invalidateHttpSession(true)
                .and()
                .addFilterBefore(filter, CsrfFilter.class)
                .csrf().disable();

    }

    /* 커스텀로그인 성공핸들러 등록 */
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomLoginSuccessHandler("/todo/list");//default로 이동할 url
    }
}