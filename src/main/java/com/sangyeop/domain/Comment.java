package com.sangyeop.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author hagome
 * @since 2019-04-12
 */
@Table
@Getter
@Entity
@Setter
@NoArgsConstructor
public class Comment implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private String content;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private ToDo toDo;

    @Builder
    public Comment(String content, LocalDateTime createdDate, LocalDateTime modifiedDate, ToDo toDo) {
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.toDo = toDo;
    }
}
