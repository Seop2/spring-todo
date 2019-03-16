package com.sangyeop.repository;

import com.sangyeop.domain.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {
}
