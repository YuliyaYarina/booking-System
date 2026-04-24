package org.example.bookingsystem.service;

import org.example.bookingsystem.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User getCurrentUser();

    List<User> findBookableUsers();

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);
}
