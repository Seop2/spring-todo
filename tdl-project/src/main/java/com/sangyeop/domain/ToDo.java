package com.sangyeop.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

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

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public ToDo(String description, Boolean status, LocalDate createdDate, LocalDate completedDate) {
        this.description = description;
        this.status = status;
        this.createdDate = createdDate;
        this.completedDate = completedDate;
    }

    public void regist() {
        this.status = false;
        this.createdDate = LocalDate.now();
    }

    public void update() {
        this.status = !this.getStatus();
        this.completedDate = this.status ? LocalDate.now() : null;
    }

    public void edit(String description) {
        this.description = description;
    }
}
