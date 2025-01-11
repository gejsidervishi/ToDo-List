package com.GesW.ToDo_List.Repository;

import com.GesW.ToDo_List.DTO.UserDTO;
import com.GesW.ToDo_List.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User save(UserDTO userDto);
}
