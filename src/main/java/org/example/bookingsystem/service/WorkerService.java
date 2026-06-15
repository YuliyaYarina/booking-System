package org.example.bookingsystem.service;

import org.example.bookingsystem.model.Worker;

import java.util.List;
import java.util.Optional;

public interface WorkerService {

    Worker getCurrentWorker();

    List<Worker> findBookableWorkers();

    Optional<Worker> findById(Long id);

    Optional<Worker> findByUsername(String username);

    Worker save(Worker worker);
}
