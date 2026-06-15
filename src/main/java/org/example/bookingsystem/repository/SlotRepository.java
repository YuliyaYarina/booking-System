package org.example.bookingsystem.repository;

import org.example.bookingsystem.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlotRepository extends JpaRepository<Slot, Long> {

}
