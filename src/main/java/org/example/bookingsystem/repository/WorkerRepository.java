package org.example.bookingsystem.repository;

import org.example.bookingsystem.model.Role;
import org.example.bookingsystem.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

    Optional<Worker> findByUsername(String username);

    List<Worker> findByRolesContaining(Role role);
}
