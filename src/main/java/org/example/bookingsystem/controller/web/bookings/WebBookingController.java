package org.example.bookingsystem.controller.web.bookings;

import org.example.bookingsystem.model.Booking;
import org.example.bookingsystem.repository.BookingRepository;
import org.example.bookingsystem.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/web/bookings")
public class WebBookingController {

    private final BookingRepository repository;
    private final UserRepository userRepository;

    public WebBookingController(BookingRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String view(Model model, Principal principal) {
        String username = principal.getName();

        if (userRepository.findByUsername(username).isEmpty()) {
            model.addAttribute("user", userRepository.findByUsername(username));
        }

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
