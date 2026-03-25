package org.example.bookingsystem.controller.api.bookings;

import org.example.bookingsystem.model.Booking;
import org.example.bookingsystem.model.User;
import org.example.bookingsystem.repository.BookingRepository;
import org.example.bookingsystem.repository.UserRepository;
import org.example.bookingsystem.service.BookingService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService service;
    private final UserRepository userRepository;

    public BookingController(BookingService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    @PostMapping
    public Booking create(@RequestBody Booking booking) {

        User user = userRepository.findByUsername("admin").orElseThrow();
        return service.create(booking, user);
    }

    @GetMapping
    public List<Booking> getAll() {
        return service.getAll();
    }

    @GetMapping("/by-phone")
    public List<Booking> getByPhone(@RequestParam String phone) {
        return service.getByPhone(phone);
    }

    @GetMapping("/by-date")
    public List<Booking> getByDate(
            @RequestParam String start,
            @RequestParam String end
    ) {
        return service.getByDate(start, end);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
