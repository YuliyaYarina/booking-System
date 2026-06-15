package org.example.bookingsystem.repository;

import org.example.bookingsystem.model.Client;
import org.example.bookingsystem.model.Role;
import org.example.bookingsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByUsername(String username);

    List<Client> findByRolesContaining(Role role);
}
