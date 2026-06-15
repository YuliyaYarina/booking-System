package org.example.bookingsystem.repository;

import org.example.bookingsystem.model.Booking;
import org.example.bookingsystem.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByClientPhone(String phone);

    List<Booking> findByBookingDateTimeBetween(LocalDateTime start, LocalDateTime end);

    List<Booking> findByBookingDateTimeBetweenAndWorkerId(LocalDateTime start, LocalDateTime end, Long workerId);

    List<Booking> findByWorker(Worker worker);

    List<Booking> findByWorkerId(Long workerId);
}
