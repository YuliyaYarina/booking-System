package org.example.bookingsystem.service;

import org.example.bookingsystem.model.Booking;
import org.example.bookingsystem.model.User;
import org.example.bookingsystem.model.Worker;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    Booking save(Booking booking);

    Booking create(Booking booking, Worker worker);

    List<Booking> getAll();

    List<Booking> getAll(Worker worker);

    List<Booking> getByPhone(String phone);

    List<Booking> getByDate(String start, String end);

    void delete(@PathVariable Long id);

    List<Booking> findByWorkerId(Long workerId);

    List<Booking> filterBookings(User currentUser, LocalDate bookingDay, Long masterId);

    Booking findById(Long id);
}