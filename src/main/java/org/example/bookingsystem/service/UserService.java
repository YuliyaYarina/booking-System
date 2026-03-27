package org.example.bookingsystem.service;

import org.example.bookingsystem.model.User;

import java.util.Optional;

public interface UserService {

    User getCurrentUser();

    Optional<User> findByUsername(String username);
}
