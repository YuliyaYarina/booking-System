package org.example.bookingsystem.service;

import org.example.bookingsystem.model.Client;

import java.util.List;
import java.util.Optional;
public interface ClientService {

    Client getCurrentClient();

    List<Client> findBookableClients();

    Optional<Client> findById(Long id);

    Optional<Client> findByUsername(String username);

    Client save(Client client);
}
