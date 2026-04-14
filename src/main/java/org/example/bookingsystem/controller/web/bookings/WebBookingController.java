package org.example.bookingsystem.controller.web.bookings;

import org.example.bookingsystem.model.Booking;
import org.example.bookingsystem.model.Role;
import org.example.bookingsystem.model.User;
import org.example.bookingsystem.service.BookingService;
import org.example.bookingsystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

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
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }
        List<Booking> bookings;

        if (currentUser.getRoles().contains(Role.ADMIN)) {
            // ADMIN видит все записи
            bookings = bookingService.getAll();
        } else {
            // USER видит только свои записи
            bookings = bookingService.findByUserId(currentUser.getId());
        }

        model.addAttribute("bookings", bookings);
        return "bookings";
    }

    @PostMapping
    public String create(
            @RequestParam String clientName,
            @RequestParam String phone,
            @RequestParam String bookingTime
    ) {
        Booking booking = new Booking();
        booking.setClientName(clientName);
        booking.setPhone(phone);
        booking.setBookingTime(LocalDateTime.parse(bookingTime));
        bookingService.create(booking, userService.getCurrentUser());
        return "redirect:/web/bookings";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Booking booking = bookingService.findById(id);
        checkOwnershipOrAdmin(booking);
        model.addAttribute("booking", booking);
        return "edit-booking";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        Booking booking = bookingService.findById(id);
        checkOwnershipOrAdmin(booking);
        bookingService.delete(id);
        return "redirect:/web/bookings";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, Booking updatedBooking) {
        Booking booking = bookingService.findById(id);
        checkOwnershipOrAdmin(booking);

        booking.setClientName(updatedBooking.getClientName());
        booking.setBookingTime(updatedBooking.getBookingTime());
        bookingService.save(booking);

        return "redirect:/web/bookings";
    }

    private void checkOwnershipOrAdmin(Booking booking) {
        User currentUser = userService.getCurrentUser();
        if (!booking.getUser().getId().equals(currentUser.getId()) &&
                !currentUser.getRoles().contains(Role.ADMIN)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}