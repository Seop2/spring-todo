package com.sangyeop.repository;

import com.sangyeop.domain.Comment;
import com.sangyeop.domain.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author hagome
 * @since 2019-04-12
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByToDo(ToDo toDo);
}
