package org.example.bookingsystem.repository;

import org.example.bookingsystem.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByPhone(String phone);

    List<Booking> findByBookingTimeBetween(LocalDateTime start, LocalDateTime end);



}
