package com.sangyeop.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

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

    @Builder
    public User(String id, String passsword, String email) {
        this.id = id;
        this.passsword = passsword;
        this.email = email;
    }

}
