package org.example.bookingsystem.controller;

import org.example.bookingsystem.model.Booking;
import org.example.bookingsystem.repository.BookingRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/web/bookings")
public class WebBookingController {

    private final BookingRepository repository;

    public WebBookingController(BookingRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String view(Model model) {
        model.addAttribute("bookings", repository.findAll());
        return "bookings";
    }

    @PostMapping
    public String create(
            @RequestParam String clientName,
            @RequestParam String phone,
            @RequestParam String bookingTime
    ) {
        Booking b = new Booking();
        b.setClientName(clientName);
        b.setPhone(phone);
        b.setBookingTime(LocalDateTime.parse(bookingTime));

        repository.save(b);

        return "redirect:/web/bookings";
    }
}
