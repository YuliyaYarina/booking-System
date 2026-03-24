package org.example.bookingsystem.controller;

import org.example.bookingsystem.model.Booking;
import org.example.bookingsystem.repository.BookingRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingRepository repository;

    public BookingController(BookingRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Booking create(@RequestBody Booking booking) {
        return repository.save(booking);
    }

    @GetMapping
    public List<Booking> getAll() {
        return repository.findAll();
    }

    @GetMapping("/by-phone")
    public List<Booking> getByPhone(@RequestParam String phone) {
        return repository.findByPhone(phone);
    }

    @GetMapping("/by-date")
    public List<Booking> getByDate(
            @RequestParam String start,
            @RequestParam String end
    ) {
        return repository.findByBookingTimeBetween(
                LocalDateTime.parse(start),
                LocalDateTime.parse(end)
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
