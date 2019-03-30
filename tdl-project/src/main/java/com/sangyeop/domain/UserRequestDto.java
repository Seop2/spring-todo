package com.sangyeop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author hagome
 * @since 2019-03-30
 */
@Getter
@Setter
public class UserRequestDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String id;

    @NotBlank(message = "비밀번호를 작성해주세요.")
    private String pw;

    @NotBlank(message = "이메일을 작성해주세요.")
    @Email(message = "메일의 양식을 지켜주세요.")
    private String email;

}
