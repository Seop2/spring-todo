package com.sangyeop.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hagome
 * @since  2019-03-29
 */
@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    @NotBlank(message = "아이디를 입력해주세요.")
    private String id;

    @Column
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Length(min = 5, message = "비밀번호는 5자리 이상")
    private String pw;

    @Column
    @NotBlank(message = "이메일를 입력해주세요.")
    @Email(message = "이메일 형식이 맞지 않습니다.")
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<ToDo> toDos = new ArrayList<>();

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="uid")
    private List<UserRole> roles;

    public void add(ToDo toDo) {
        this.toDos.add(toDo);
        toDo.setUser(this);
    }

    @Builder
    public User(String id, String pw, String email, List<ToDo> toDos, List<UserRole> roles) {
        this.id = id;
        this.pw = pw;
        this.email = email;
        this.toDos = toDos;
        this.roles = roles;
    }
}