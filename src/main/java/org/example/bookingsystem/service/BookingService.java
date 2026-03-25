package org.example.bookingsystem.service;

import org.example.bookingsystem.model.Booking;
import org.example.bookingsystem.model.User;
import org.example.bookingsystem.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository repository;

    public BookingService(BookingRepository repository) {
        this.repository = repository;
    }

    public Booking create(Booking booking, User user) {
        booking.setUser(user);
        return repository.save(booking);
    }

    public List<Booking> getAll() {
        return repository.findAll();
    }

    public List<Booking> getByPhone(String phone) {
        return repository.findByPhone(phone);
    }

    public List<Booking> getByDate(String start, String end) {
        return repository.findByBookingTimeBetween(
                LocalDateTime.parse(start),
                LocalDateTime.parse(end)
        );
    }

    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    public List<Booking> getAll(User user) {
        return repository.findByUser(user);
    }
}
