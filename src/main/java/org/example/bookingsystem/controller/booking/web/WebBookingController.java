package org.example.bookingsystem.controller.booking.web;

import org.example.bookingsystem.model.Booking;
import org.example.bookingsystem.model.Role;
import org.example.bookingsystem.model.User;
import org.example.bookingsystem.model.Worker;
import org.example.bookingsystem.service.BookingService;
import org.example.bookingsystem.service.impl.WorkerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/web/bookings")
public class WebBookingController {

    private final BookingService bookingService;
    private final WorkerServiceImpl workerService;

    public WebBookingController(BookingService bookingService, WorkerServiceImpl workerService) {
        this.bookingService = bookingService;
        this.workerService = workerService;
    }

    @GetMapping
    public String view(
            @RequestParam(required = false) String bookingDay,
            @RequestParam(required = false) Long masterId,
            Model model
    ) {
        Worker currentUser = workerService.getCurrentWorker();
        if (currentUser == null) {
            return "redirect:/login";
        }

        LocalDate selectedDay = parseDay(bookingDay);
        if (selectedDay == null) {
            selectedDay = LocalDate.now();
        }
        boolean isAdmin = currentUser.getRoles().contains(Role.ADMIN);
        Long selectedMasterId = isAdmin ? masterId : null;

        List<Booking> bookings = bookingService.filterBookings(
                currentUser,
                selectedDay,
                selectedMasterId
        );

        List<Booking> upcomingBookings = bookings;
        List<Booking> passedBookings = List.of();
        LocalDateTime now = LocalDateTime.now();
        if (selectedDay.equals(LocalDate.now())) {
            upcomingBookings = bookings.stream()
                    .filter(booking -> !booking.getBookingDateTime().isBefore(now))
                    .toList();
            passedBookings = bookings.stream()
                    .filter(booking -> booking.getBookingDateTime().isBefore(now))
                    .toList();
        }

        model.addAttribute("bookings", bookings);
        model.addAttribute("upcomingBookings", upcomingBookings);
        model.addAttribute("passedBookings", passedBookings);
        model.addAttribute("selectedBookingDay", selectedDay.toString());
        model.addAttribute("selectedMasterId", selectedMasterId);
        model.addAttribute("isAdmin", isAdmin);

        if (isAdmin) {
            model.addAttribute("masters", workerService.findBookableWorkers());
        }

        return "bookings";
    }

    @PostMapping
    public String create(
            @RequestParam String workDescription,
            @RequestParam String bookingTime
    ) {
        Booking booking = new Booking();
        booking.setWorkDescription(workDescription);
        booking.setBookingDateTime(LocalDateTime.parse(bookingTime));
        bookingService.create(booking, workerService.getCurrentWorker());
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
                         @RequestParam String workDescription,
                         @RequestParam String bookingTime
    ) {
        Booking booking = bookingService.findById(id);
        checkOwnershipOrAdmin(booking);

        booking.setWorkDescription(workDescription);
        booking.setBookingDateTime(LocalDateTime.parse(bookingTime));
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
        User currentUser = workerService.getCurrentWorker();
        if (!booking.getWorker().getId().equals(currentUser.getId()) &&
                !currentUser.getRoles().contains(Role.ADMIN)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}