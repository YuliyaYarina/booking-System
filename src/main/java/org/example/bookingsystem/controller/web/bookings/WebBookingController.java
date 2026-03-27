package org.example.bookingsystem.controller.web.bookings;

import org.example.bookingsystem.model.Booking;
import org.example.bookingsystem.model.User;
import org.example.bookingsystem.service.BookingService;
import org.example.bookingsystem.service.UserService;
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

    private final BookingService bookingService;
    private final UserService userService;

    public WebBookingController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @GetMapping
    public String view(Model model, Principal principal) {
        String username = principal.getName();

        if (userService.findByUsername(username).isEmpty()) {
            model.addAttribute("user", userService.findByUsername(username));
        }
        User user = userService.getCurrentUser();

        model.addAttribute("bookings", bookingService.findByUserId(user.getId()));
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

        bookingService.create(b, userService.getCurrentUser());

        return "redirect:/web/bookings";
    }
}
