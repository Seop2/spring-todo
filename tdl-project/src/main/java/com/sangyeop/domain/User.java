package com.sangyeop.domain;

import lombok.*;

import javax.persistence.*;
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
    private String id;

    @Column
    private String pw;

    @Column
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<ToDo> toDos = new ArrayList<>();

    public void add(ToDo toDo) {
        this.toDos.add(toDo);
        toDo.setUser(this);
    }

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="uid")
    private List<UserRole> roles;

    @Builder
    public User(String id, String pw, String email, List<ToDo> toDos, List<UserRole> roles) {
        this.id = id;
        this.pw = pw;
        this.email = email;
        this.toDos = toDos;
        this.roles = roles;
    }
}