package org.example.bookingsystem.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.bookingsystem.exceptions.UserAlreadyExistsException;
import org.example.bookingsystem.exceptions.UserNotFoundException;
import org.example.bookingsystem.model.Client;
import org.example.bookingsystem.model.Role;
import org.example.bookingsystem.repository.ClientRepository;
import org.example.bookingsystem.service.ClientService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public Client getCurrentClient() {

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
    public List<Client> findBookableClients() {
        return clientRepository.findByRolesContaining(Role.CLIENT);
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Optional<Client> findByUsername(String clientName) {
        return clientRepository.findByUsername(clientName);
    }

    @Override
    @Transactional
    public Client save(Client client) {

        Objects.requireNonNull(client, "User must not be null");

        Objects.requireNonNull(
                client.getUsername(),
                "Username must not be null"
        );

        try {

            return clientRepository.save(client);

        } catch (DataIntegrityViolationException e) {

            throw new UserAlreadyExistsException(
                    client.getUsername()
            );
        }
    }
}
