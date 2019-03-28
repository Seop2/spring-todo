package com.sangyeop.repository;

import com.sangyeop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hagome
 * @since  2019-03-29
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(String id);
}
