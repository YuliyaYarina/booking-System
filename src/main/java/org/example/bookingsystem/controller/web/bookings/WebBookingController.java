package org.example.bookingsystem.controller.web.bookings;

import org.example.bookingsystem.model.Booking;
import org.example.bookingsystem.model.Role;
import org.example.bookingsystem.model.User;
import org.example.bookingsystem.service.BookingService;
import org.example.bookingsystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
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
    public String view(
            @RequestParam(required = false) String bookingDay,
            @RequestParam(required = false) Long masterId,
            Model model
    ) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        LocalDate selectedDay = parseDay(bookingDay);
        boolean isAdmin = currentUser.getRoles().contains(Role.ADMIN);
        Long selectedMasterId = isAdmin ? masterId : null;

        List<Booking> bookings = bookingService.filterBookings(
                currentUser,
                selectedDay,
                selectedMasterId
        );

        model.addAttribute("bookings", bookings);
        model.addAttribute("selectedBookingDay", bookingDay);
        model.addAttribute("selectedMasterId", selectedMasterId);
        model.addAttribute("isAdmin", isAdmin);

        if (isAdmin) {
            model.addAttribute("masters", userService.findBookableUsers());
        }

        return "bookings";
    }

    @PostMapping
    public String create(
            @RequestParam String clientName,
            @RequestParam String phone,
            @RequestParam String workDescription,
            @RequestParam String bookingTime
    ) {
        Booking booking = new Booking();
        booking.setClientName(clientName);
        booking.setPhone(phone);
        booking.setWorkDescription(workDescription);
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
    public String update(@PathVariable Long id,
                         @RequestParam String clientName,
                         @RequestParam String phone,
                         @RequestParam String workDescription,
                         @RequestParam String bookingTime
    ) {
        Booking booking = bookingService.findById(id);
        checkOwnershipOrAdmin(booking);

        booking.setClientName(clientName);
        booking.setPhone(phone);
        booking.setWorkDescription(workDescription);
        booking.setBookingTime(LocalDateTime.parse(bookingTime));
        bookingService.save(booking);

        return "redirect:/web/bookings";
    }

    private LocalDate parseDay(String bookingDay) {
        if (bookingDay == null || bookingDay.isBlank()) {
            return null;
        }
        return LocalDate.parse(bookingDay);
    }

    private void checkOwnershipOrAdmin(Booking booking) {
        User currentUser = userService.getCurrentUser();
        if (!booking.getUser().getId().equals(currentUser.getId()) &&
                !currentUser.getRoles().contains(Role.ADMIN)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}