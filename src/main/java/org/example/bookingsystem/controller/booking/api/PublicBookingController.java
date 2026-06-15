package org.example.bookingsystem.controller.booking.api;

import org.example.bookingsystem.model.Booking;
import org.example.bookingsystem.model.Worker;
import org.example.bookingsystem.service.BookingService;
import org.example.bookingsystem.service.WorkerService;
import org.example.bookingsystem.service.impl.WorkerServiceImpl;
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
    private final WorkerService workerService;

    public PublicBookingController(BookingService bookingService, WorkerServiceImpl workerService) {
        this.bookingService = bookingService;
        this.workerService = workerService;
    }

    @GetMapping("/public/booking")
    public String bookingPage(Model model) {
        model.addAttribute("users", workerService.findBookableWorkers());
        return "public-booking";
    }

    @PostMapping("/public/booking")
    public String createPublicBooking(@RequestParam String workDescription,
                                      @RequestParam String bookingTime,
                                      @RequestParam Long userId) {
        Worker selectedUser = workerService.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Пользователь не найден"));

        Booking booking = new Booking();
        booking.setWorkDescription(workDescription);
        booking.setBookingDateTime(LocalDateTime.parse(bookingTime));
        bookingService.create(booking, selectedUser);

        return "redirect:/public/booking?success=true";
    }
}