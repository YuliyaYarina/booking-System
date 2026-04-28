package org.example.bookingsystem.service.impl;

import org.example.bookingsystem.model.User;
import org.example.bookingsystem.repository.UserRepository;
import org.example.bookingsystem.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.example.bookingsystem.model.Role;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getCurrentUser() {
        String username = Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                .getName();

        return userRepository.findByUsername(username)
                .orElseThrow();
    }

    @Override
    public List<User> findBookableUsers() {
        return userRepository.findByRolesContaining(Role.USER);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
