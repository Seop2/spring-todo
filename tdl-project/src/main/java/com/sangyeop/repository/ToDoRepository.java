package com.sangyeop.repository;

import com.sangyeop.domain.ToDo;
import com.sangyeop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author hagome
 * @since  2019-03-29
 */
public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    List<ToDo> findByUserOrderByIdx(User user);
}
