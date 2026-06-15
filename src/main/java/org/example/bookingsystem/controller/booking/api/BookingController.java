package org.example.bookingsystem.controller.booking.api;

import org.example.bookingsystem.model.Booking;
import org.example.bookingsystem.model.Worker;
import org.example.bookingsystem.service.BookingService;
import org.example.bookingsystem.service.impl.WorkerServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final WorkerServiceImpl workerService;

    public BookingController(WorkerServiceImpl workerService, BookingService service) {
        this.bookingService = service;
        this.workerService = workerService;
    }

    @PostMapping
    public Booking create(@RequestBody Booking booking) {

        Worker worker = (Worker) workerService.getCurrentWorker();
        return bookingService.create(booking, worker);
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
