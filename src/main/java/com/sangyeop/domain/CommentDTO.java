package com.sangyeop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author hagome
 * @since 2019-04-18
 */

@Data
@NoArgsConstructor
public class CommentDTO {
    private Long idx;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public CommentDTO(Comment comment) {
        this.idx = comment.getIdx();
        this.content = comment.getContent();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
    }
}
