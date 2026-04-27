package org.example.bookingsystem.repository;

import org.example.bookingsystem.model.User;
import org.example.bookingsystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findByRolesContaining(Role role);
}
