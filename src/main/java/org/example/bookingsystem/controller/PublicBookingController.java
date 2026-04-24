package org.example.bookingsystem.controller;

import org.example.bookingsystem.model.Booking;
import org.example.bookingsystem.model.User;
import org.example.bookingsystem.service.BookingService;
import org.example.bookingsystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Controller
public class PublicBookingController {

    private final BookingService bookingService;
    private final UserService userService;

    public PublicBookingController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @GetMapping("/public/booking")
    public String bookingPage(Model model) {
        model.addAttribute("users", userService.findBookableUsers());
        return "public-booking";
    }

    @PostMapping("/public/booking")
    public String createPublicBooking(@RequestParam String clientName,
                                      @RequestParam String phone,
                                      @RequestParam String bookingTime,
                                      @RequestParam Long userId) {
        User selectedUser = userService.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Пользователь не найден"));

        Booking booking = new Booking();
        booking.setClientName(clientName);
        booking.setPhone(phone);
        booking.setBookingTime(LocalDateTime.parse(bookingTime));
        bookingService.create(booking, selectedUser);

        return "redirect:/public/booking?success=true";
    }
}
