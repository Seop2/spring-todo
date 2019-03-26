package com.sangyeop.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@Getter
@ToString
public class User implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private String id;

    @Column
    private String passsword;

    @Column
    private String email;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<ToDo> toDos = new ArrayList<>();

    public void add(ToDo toDo) {
        this.toDos.add(toDo);
        toDo.setUser(this);
    }

    @Builder
    public User(String id, String passsword, String email, List<ToDo> toDos) {
        this.id = id;
        this.passsword = passsword;
        this.email = email;
        this.toDos = toDos;
    }

}