package org.example.bookingsystem.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.bookingsystem.exceptions.UserAlreadyExistsException;
import org.example.bookingsystem.exceptions.UserNotFoundException;
import org.example.bookingsystem.model.Worker;
import org.example.bookingsystem.repository.WorkerRepository;
import org.example.bookingsystem.service.WorkerService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.example.bookingsystem.model.Role;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;

    @Override
    public Worker getCurrentWorker() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null) {
            throw new AuthenticationCredentialsNotFoundException(
                    "No authenticated user"
            );
        }

        String username = authentication.getName();

        return findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException(username));
    }

    @Override
    public List<Worker> findBookableWorkers() {
        return workerRepository.findByRolesContaining(Role.WORKER);
    }

    @Override
    public Optional<Worker> findById(Long id) {
        return workerRepository.findById(id);
    }

    @Override
    public Optional<Worker> findByUsername(String userName) {
        return workerRepository.findByUsername(userName);
    }

    @Override
    @Transactional
    public Worker save(Worker worker) {

        Objects.requireNonNull(worker, "User must not be null");

        Objects.requireNonNull(
                worker.getUsername(),
                "Username must not be null"
        );

        try {

            return workerRepository.save(worker);

        } catch (DataIntegrityViolationException e) {

            throw new UserAlreadyExistsException(
                    worker.getUsername()
            );
        }
    }
}
