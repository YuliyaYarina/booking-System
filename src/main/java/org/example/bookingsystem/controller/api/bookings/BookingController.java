package org.example.bookingsystem.controller.api.bookings;

import org.example.bookingsystem.model.Booking;
import org.example.bookingsystem.model.User;
import org.example.bookingsystem.service.BookingService;
import org.example.bookingsystem.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;

    public BookingController(UserService userService, BookingService service) {
        this.bookingService = service;
        this.userService = userService;
    }

    @PostMapping
    public Booking create(@RequestBody Booking booking) {

        User user = userService.getCurrentUser();
        return bookingService.create(booking, user);
    }

    @GetMapping
    public List<Booking> getAll() {
        return bookingService.getAll();
    }

    @GetMapping("/by-phone")
    public List<Booking> getByPhone(@RequestParam String phone) {
        return bookingService.getByPhone(phone);
    }

    @GetMapping("/by-date")
    public List<Booking> getByDate(
            @RequestParam String start,
            @RequestParam String end
    ) {
        return bookingService.getByDate(start, end);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookingService.delete(id);
    }
}
