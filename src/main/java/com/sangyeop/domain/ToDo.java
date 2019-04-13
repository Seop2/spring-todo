package com.sangyeop.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hagome
 * @since  2019-03-29
 */
@NoArgsConstructor
@Entity
@Getter
@Table
@Setter
public class ToDo implements Serializable {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private String description;

    @Column
    private Boolean status;

    @Column
    private LocalDate createdDate;

    @Column
    private LocalDate completedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "toDo")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public ToDo(String description, Boolean status, LocalDate createdDate, LocalDate completedDate, User user, List<Comment> comments) {
        this.description = description;
        this.status = status;
        this.createdDate = createdDate;
        this.completedDate = completedDate;
        this.user = user;
        this.comments = comments;
    }

    public void regist() {
        this.status = false;
        this.createdDate = LocalDate.now();
    }

    public void update() {
        this.status = !this.getStatus();
        this.completedDate = this.status ? LocalDate.now() : null;
    }

    public void add(Comment comment){
        this.comments.add(comment);
        comment.setToDo(this);
    }

    public void switchStatus(String description) {
        this.description = description;
    }
}
